package ModeSveta;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.neuroph.core.Layer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.Neuron;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.LMS;


public class NeuronovaSit {

    private static DataSet trainingSet, testSet, neznamoSet, allSet;

    public static void main(String args[]) {

        // create training set (logical AND function)
        trainingSet = new DataSet(3, 1);
        testSet = new DataSet(3, 1);
        neznamoSet = new DataSet(3, 1);
        allSet = new DataSet(3, 1);
        nacteniDat();
        Layer inputLayer = new Layer();
        inputLayer.addNeuron(new Neuron());
        inputLayer.addNeuron(new Neuron());
        inputLayer.addNeuron(new Neuron());

        Layer hiddenLayerOne = new Layer();
        hiddenLayerOne.addNeuron(new Neuron());
        hiddenLayerOne.addNeuron(new Neuron());
        hiddenLayerOne.addNeuron(new Neuron());


        Layer hiddenLayerTwo = new Layer();
        hiddenLayerTwo.addNeuron(new Neuron());
        hiddenLayerTwo.addNeuron(new Neuron());
        hiddenLayerTwo.addNeuron(new Neuron());

        Layer outputLayer = new Layer();
        outputLayer.addNeuron(new Neuron());


        int maxIterations = 100000;
        NeuralNetwork myPerceptron = new MultiLayerPerceptron(3, 9, 1);
        ((LMS) myPerceptron.getLearningRule()).setMaxError(0.001);//0-1
        ((LMS) myPerceptron.getLearningRule()).setLearningRate(0.7);//0-1
        ((LMS) myPerceptron.getLearningRule()).setMaxIterations(maxIterations);//0-1

        System.out.println("zacinam se ucit");
        myPerceptron.learn(trainingSet);

        System.out.println("Testing loaded perceptron");
        testNeuralNetwork(myPerceptron, allSet);

    }


    private static void nacteniDat() {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("../3600dataset.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(Arrays.asList(values));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        records.remove(0);
        if (records.isEmpty() == false) {
            int x = 1;

            for (List<String> radek : records) {
                Double hlad = Double.parseDouble(radek.get(0));
                Double zizen = Double.parseDouble(radek.get(1));
                Double energie = Double.parseDouble(radek.get(2));
                Double hodnoceni = Double.parseDouble(radek.get(3));
                hlad = hlad / 100;
                zizen = zizen / 100;
                energie = energie / 100;
                if (hodnoceni > 1)
                    hodnoceni = 1.0d;
                if (x == 1 || x == 3 || x == 5) {
                    neznamoSet.add(new DataSetRow(new double[]{
                            hlad, zizen, energie},
                            new double[]{hodnoceni}));
                    allSet.add(new DataSetRow(new double[]{
                            hlad, zizen, energie},
                            new double[]{hodnoceni}));

                }//trenovací set
                if (x == 2) {

                    trainingSet.add(new DataSetRow(new double[]{
                            hlad, zizen, energie},
                            new double[]{hodnoceni}));
                    allSet.add(new DataSetRow(new double[]{
                            hlad, zizen, energie},
                            new double[]{hodnoceni}));
                }
                //testovaci set
                if (x == 4) {

                    testSet.add(new DataSetRow(new double[]{
                            hlad, zizen, energie},
                            new double[]{hodnoceni}));
                    allSet.add(new DataSetRow(new double[]{
                            hlad, zizen, energie},
                            new double[]{hodnoceni}));
                }

                x++;
                if (x > 5)
                    x = 1;
            }
        }

    }

    /**
     * Prints network output for the each element from the specified training set.
     *
     * @param neuralNet neural network
     * @param testSet   data set used for testing
     */
    public static void testNeuralNetwork(NeuralNetwork neuralNet, DataSet testSet) {
        try {


            FileWriter csvWriter = new FileWriter("vystup.csv");

            csvWriter.append("Vstup");
            csvWriter.append(",");
            csvWriter.append("Hodnoceni z simulace");
            csvWriter.append(",");
            csvWriter.append("Odhad");
            csvWriter.append("\n");


            for (DataSetRow trainingElement : testSet.getRows()) {
                neuralNet.setInput(trainingElement.getInput());
                neuralNet.calculate();
                double[] networkOutput = neuralNet.getOutput();

                csvWriter.append(Arrays.toString((trainingElement.getInput())));
                csvWriter.append(",");
                csvWriter.append(Arrays.toString((trainingElement.getDesiredOutput())));
                csvWriter.append(",");
                csvWriter.append(Arrays.toString((networkOutput)));

                csvWriter.append("\n");
                System.out.print("Input: " + Arrays.toString(trainingElement.getInput()));
                System.out.print("Simulace: " + Arrays.toString(trainingElement.getDesiredOutput()));
                System.out.println(" Output: " + Arrays.toString(networkOutput));
            }


            csvWriter.flush();
        } catch (Exception e) {

        }

    }


}
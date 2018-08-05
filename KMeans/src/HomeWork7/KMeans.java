package HomeWork7;

import java.util.Random;

import weka.core.Instance;
import weka.core.Instances;

public class KMeans 
{
	public Instance[] centroids;
	public Instances[] clusters;

	public void buildClusterModel(Instances data, int numberOfMeans) 
	{
		centroids = new Instance[numberOfMeans];
		initClusters(data);
		initCentroids(data);
		findKMeansCentroids(data);
	}
	
	public void initCentroids(Instances data) 
	{
		for(int i=0; i<centroids.length; i++)
		{
			int centroid = new Random().nextInt(data.numInstances());
			centroids[i] = data.get(centroid);
		}
	}
	
	public void findKMeansCentroids(Instances data) 
	{
		// Default number of iterations
		for(int i=0; i<40; i++)
		{
			// Assign clusters
			for (Instance instance : data) 
			{
				clusters[findClosestCentroid(instance)].add(instance);
			}
			
			// Calculate the new centroids
			calculateNewCentroids();
			if(centroids.length == 5) 
			{
				System.out.println(calcAvgWSSSE(data));
			}
			initClusters(data);
		}
	}
	
	public void calculateNewCentroids() 
	{
		for(int i=0; i<clusters.length; i++) 
		{
			int rSum=0,gSum=0,bSum=0;
			for (Instance instance : clusters[i])
			{
				rSum += instance.value(1);
				gSum += instance.value(2);
				bSum += instance.value(3);
			}
			if(clusters[i].numInstances() > 0) 
			{
				// Divide each centroid's r,g,b value by the number of instances to get the mean
				centroids[i].setValue(1, rSum/clusters[i].numInstances());
				centroids[i].setValue(2, gSum/clusters[i].numInstances());
				centroids[i].setValue(3, bSum/clusters[i].numInstances());
			}
		}
	}
	
	public void initClusters(Instances data) 
	{
		clusters = new Instances[centroids.length];
		for(int i=0; i<clusters.length; i++)
		{
			clusters[i] = new Instances(data,0,0);
		}
	}
	
	private double calcSquaredDistanceFromCentroid(Instance instance, Instance centroid) 
	{
		return Math.pow(instance.value(1) - centroid.value(1), 2) + Math.pow(instance.value(2) - centroid.value(2), 2) + Math.pow(instance.value(3) - centroid.value(3), 2);
	}
	
	private int findClosestCentroid(Instance instance) 
	{
		int clusterIndex = Integer.MIN_VALUE;
		double min = Double.MAX_VALUE;
		for(int i=0; i<centroids.length; i++)
		{
			if(calcSquaredDistanceFromCentroid(instance, centroids[i]) < min) 
			{
				clusterIndex = i;
				min = calcSquaredDistanceFromCentroid(instance, centroids[i]);
			}
		}
		return clusterIndex;
	}
	
	public Instances quantize(Instances data) 
	{
		Instances quantizedData = new Instances(data);
		for(int i=0; i<quantizedData.numInstances(); i++)
		{
			// Find the index of the closest centroid to each instance
			int centroidIndex = findClosestCentroid(quantizedData.get(i));
			// Set the R,G,B values of the i'th index to the centroid's R,G,B values to quantize the photo
			quantizedData.get(i).setValue(1, centroids[centroidIndex].value(1));
			quantizedData.get(i).setValue(2, centroids[centroidIndex].value(2));
			quantizedData.get(i).setValue(3, centroids[centroidIndex].value(3));
		}
		return quantizedData;
	}
	
	public double calcAvgWSSSE(Instances data) 
	{
		double WSSSEsum = 0;
		for(int i=0; i<clusters.length; i++)
		{
			for(int j=0; j<clusters[i].numInstances(); j++)
			{
				WSSSEsum += calcSquaredDistanceFromCentroid(clusters[i].get(j), centroids[i]);
			}
		}
		return (double) WSSSEsum/data.numInstances();
	}
}

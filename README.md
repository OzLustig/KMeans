In this assignment you will
implement the K-means algorithm and use it to transform an image to a quantized
version thereof. In the second part you will use a pre-implemented PCA class in
order to compress data, and determine the difference between the original data
and the decompressed data (the decompressed data is the compressed data
transformed back to the original space).



K-means:



Color image quantization is
the process of transforming an image so that it can be represented with a small
subset of colors; a subset smaller than the image's original palette size.
Image quantization is a form of lossy data compression since the number of bits
used for the palette is reduced. During this compression some data is lost and
therefore we must use a careful algorithm that will minimize the perceived
difference between the original image and its quantized counterpart. Other than
compression, image quantization plays an important role in image recognition
algorithms.



When representing images in
true color, each pixel is represented by 3 RGB components and each one of the 3
RGB components is represented by 8 bits and can take a value from 0 to 255.
This means that in true color representation one can use  Million different colors. In this exercise we
will perform image quantization from images given in true color into 256, 16 or
even less colors.



There are many algorithms for
quantization. You are going to use K-means in color spaces to do this. K-means
assumes that we know the number of clusters to be found (denoted by k), and the
algorithm progresses until the means of the clusters are found (or
approximated). 



In k-means the color assigned
to each pixel is the color of the mean (centroid) of the cluster. This is a
point in the RGB space.



The downside of k-means is
that starting with different cluster center locations can lead to different
local optima! 



To summarize, every pixel in
the image is a point in the three dimensional RGB space. We want to cluster
these points into k clusters. We then replace each pixel by the centroid of the
cluster to which it was assigned.



PCA:



You will also use PCA to do
data compression. PCA is a method to reduce the dimensionality of your data by
finding the rotation of the data that best de-correlates them. The new axes are
called Principal Components (PCs). You will reduce the data dimensionality by
creating a projection matrix and using only j of the PCs so we are left
with j dimensions. We denote A as the PCA matrix (as shown in class) and
 as the matrix using only the j PCs
corresponding to the highest eigenvalues. Then the new ‘compacted’ (lower)
j-dimensional vector data is  where is the average vector of all the
features. These vectors will have 0 in all dimensions from j+1 to n. However,
to get back the instance in the original axes you need to multiply back by the
inverse of the original matrix . A ‘transformed’ instance is
an instance that was projected with PCA to a lower dimensional subspace and
then embedded back in the original space with the inverse process. You will try
this process using different numbers of principal components. For each such
dimension you will measure how far, by Euclidean distance, the transformed
instances are from the original instances. This will give you a measure of how
much information is lost when using only j PCs.

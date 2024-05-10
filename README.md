Read Me 
In the Java implemented version of DES, I implemented the structures for the Initial Permutation(IP), Finial Permutation(IP-1), Expansion Function(E) , Permutation(P), Permuted Choice(PC-1 or KEY_PERMUTATION), Permuted Choice 2(PC-2), Substitution boxes(S-boxes), Key Generation, and the bit rotations, from Wikipedia. In the Java code, I imported Java.util.Arrays so that I use these Arrays comfortably. From line 177 to 203, I am starting to make my generatekeys method. This method, thanks to the KEY_PERMUTATION_TABLE as well, will allow me to make a master key. Within this method, we are converting the secret keys to bits, applying the PC-1 permutation, splitting the keys in half, and from all of those, making the subkeys. While generating this, the code is performing the rotation to the left, and then applying the PC-2 permutation to then generate the subkeys. Next method is the encryption method. This method basically encrypts the plaintext and returns the encrypted bits to string. This method calls other methods, which we called the helper methods, to help this encrypt method work. It basically did the same thing for the decryption version of this. Next couple of methods are the helper methods as mentioned earlier ago, such as the XOR method to help calculate XOR. The expansion method to help expand the bits. The substitute method to help perform substitution using the S-boxes. The permutation helper method is to just help perform permutation. I also added some extra helper methods near the beginning because we forgot about them. From line 153 to line 165 we added these helper methods to perform the rotation and combine the 2 halves respectively. To run this code, all you have to do is go to the line after 123, where it says 'Run|Debug' and click run. 








Reference 
https://en.wikipedia.org/wiki/DES_supplementary_material 

import java.util.Arrays;

public class DES2 {

    private static final int[] INITIAL_PERMUTATION_TABLE = {
        58, 50, 42, 34, 26, 18, 10, 2,
        60, 52, 44, 36, 28, 20, 12, 4,
        62, 54, 46, 38, 30, 22, 14, 6,
        64, 56, 48, 40, 32, 24, 16, 8,
        57, 49, 41, 33, 25, 17, 9, 1,
        59, 51, 43, 35, 27, 19, 11, 3,
        61, 53, 45, 37, 29, 21, 13, 5,
        63, 55, 47, 39, 31, 23, 15, 7
    };
    private static final int[] FINAL_PERMUTATION_TABLE = {
        40, 8, 48, 16, 56, 24, 64, 32,
        39, 7, 47, 15, 55, 23, 63, 31,
        38, 6, 46, 14, 54, 22, 62, 30,
        37, 5, 45, 13, 53, 21, 61, 29,
        36, 4, 44, 12, 52, 20, 60, 28,
        35, 3, 43, 11, 51, 19, 59, 27,
        34, 2, 42, 10, 50, 18, 58, 26,
        33, 1, 41, 9, 49, 17, 57, 25
    };
    private static final int[][] S1 = {
        // S1
        {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
        {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
        {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
        {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
        // Add remaining rows
    };
    private static final int[][] S2 = {
        // S2
        {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
        {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
        {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
        {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
    };
    private static final int[][] S3 = {
        // S3
        {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
        {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
        {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
        {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
    };
    private static final int[][] S4 = {
        //S4
        {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
        {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
        {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
        {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
    };
    private static final int[][] S5 = {
        //S5
        {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
        {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
        {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
        {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
    };
    private static final int[][] S6 = {
        //S6
        {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
        {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
        {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
        {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
    };
    private static final int[][] S7 = {
        //S7
        {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
        {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
        {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
        {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
    };
    private static final int[][] S8 = {
        //S8
        {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
        {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
        {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
        {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
};
    private static final int[] KEY_PERMUTATION_TABLE = {
        57, 49, 41, 33, 25, 17, 9, 1,
        58, 50, 42, 34, 26, 18, 10, 2,
        59, 51, 43, 35, 27, 19, 11, 3,
        60, 52, 44, 36, 63, 55, 47, 39,
        31, 23, 15, 7, 62, 54, 46, 38,
        30, 22, 14, 6, 61, 53, 45, 37,
        29, 21, 13, 5, 28, 20, 12, 4
    };
    private static final int[] KEY_SHIFT_TABLE = {
        1, 1, 2, 2, 2, 2, 2, 2,
        1, 2, 2, 2, 2, 2, 2, 1
    };
    private static final int[] PC_2_PERMUTATION_TABLE = {
        14, 17, 11, 24, 1, 5, 3, 28,
        15, 6, 21, 10, 23, 19, 12, 4,
        26, 8, 16, 7, 27, 20, 13, 2,
        41, 52, 31, 37, 47, 55, 30, 40,
        51, 45, 33, 48, 44, 49, 39, 56,
        34, 53, 46, 42, 50, 36, 29, 32
    };
    private static final int[] P_BOX = {
        16,  7, 20, 21,
        29, 12, 28, 17,
        1, 15, 23, 26,
        5, 18, 31, 10,
        2,  8, 24, 14,
        32, 27,  3, 9,
        19, 13, 30, 6,
        22, 11,  4, 25
};
    private static final int[] EXPANSION_TABLE = {
        32,  1,  2,  3,  4,  5,
         4,  5,  6,  7,  8,  9,
         8,  9, 10, 11, 12, 13,
        12, 13, 14, 15, 16, 17,
        16, 17, 18, 19, 20, 21,
        20, 21, 22, 23, 24, 25,
        24, 25, 26, 27, 28, 29,
        28, 29, 30, 31, 32,  1
    };

    public static void main(String[] args) {
        String plaintext = "Hello, DES!";
        String secretKey = "SecretKey";

        // Generate subkeys
        int[][] subkeys = generateSubkeys(secretKey);

        // Encrypt plaintext
        String ciphertext = encrypt(plaintext, subkeys);

        // Decrypt ciphertext
        String decryptedPlaintext = decrypt(ciphertext, subkeys);

        // Display results
        System.out.println("Plaintext: " + plaintext);
        System.out.println("Ciphertext: " + ciphertext);
        System.out.println("Decrypted plaintext: " + decryptedPlaintext);
    }

    private static int[] stringToBits(String input) {
        byte[] bytes = input.getBytes();
        int[] bits = new int[bytes.length * 8];
        for (int i = 0; i < bytes.length; i++) {
            for (int j = 0; j < 8; j++) {
                bits[(i * 8) + j] = (bytes[i] >> (7 - j)) & 1;
            }
        }
        return bits;
    }
    // Helper method to perform circular left shift
    private static int[] circularLeftShift(int[] input, int shift) {
        int[] output = new int[input.length];
        System.arraycopy(input, shift, output, 0, input.length - shift);
        System.arraycopy(input, 0, output, input.length - shift, shift);
        return output;
    }
    private static int[] combineHalves(int[] leftHalf, int[] rightHalf) {
        int[] combined = new int[leftHalf.length + rightHalf.length];
        System.arraycopy(leftHalf, 0, combined, 0, leftHalf.length);
        System.arraycopy(rightHalf, 0, combined, leftHalf.length, rightHalf.length);
        return combined;
    }
    // Convert an array of bits to a string
    private static String bitsToString(int[] bits) {
        StringBuilder sb = new StringBuilder();
        for (int bit : bits) {
            sb.append(bit);
        }
        return sb.toString();
    }

    

    private static int[][] generateSubkeys(String secretKey) {
        // Convert secret key to bits
        int[] keyBits = stringToBits(secretKey);
        
        // Apply PC-1 permutation
        int[] permutedKey = permutation(keyBits, KEY_PERMUTATION_TABLE);
        
        // Split key into halves
        int[] leftHalf = Arrays.copyOfRange(permutedKey, 0, 28);
        int[] rightHalf = Arrays.copyOfRange(permutedKey, 28, 56);
        
        // Generate subkeys
        int[][] subkeys = new int[16][48];
        for (int round = 0; round < 16; round++) {
            // Perform circular left shift on key halves
            leftHalf = circularLeftShift(leftHalf, KEY_SHIFT_TABLE[round]);
            rightHalf = circularLeftShift(rightHalf, KEY_SHIFT_TABLE[round]);
            
            // Combine left and right halves
            int[] combinedKey = combineHalves(leftHalf, rightHalf);
            
            // Apply PC-2 permutation to generate subkey
            subkeys[round] = permutation(combinedKey, PC_2_PERMUTATION_TABLE);
        }
        
        return subkeys;
    }
    

    private static String encrypt(String plaintext, int[][] subkeys) {
        // Convert plaintext to bits
        int[] plaintextBits = stringToBits(plaintext);
        
        // Apply initial permutation
        int[] permutedPlaintext = permutation(plaintextBits, INITIAL_PERMUTATION_TABLE);
        
        // Split permuted plaintext into halves
        int[] leftHalf = Arrays.copyOfRange(permutedPlaintext, 0, 32);
        int[] rightHalf = Arrays.copyOfRange(permutedPlaintext, 32, 64);
        
        // 16 rounds of Feistel network
        for (int round = 0; round < 16; round++) {
            // Expansion
            int[] expandedRightHalf = expansion(rightHalf, EXPANSION_TABLE);
            
            // Key Mixing (XOR with subkey)
            int[] xorResult = xor(expandedRightHalf, subkeys[round]);
            
            // Substitution using S-boxes
            int[] substituted = substitute(xorResult);
            
            // Permutation using P-box
            int[] permuted = permutation(substituted, P_BOX);
            
            // XOR with left half
            int[] temp = Arrays.copyOf(rightHalf, rightHalf.length);
            rightHalf = xor(leftHalf, permuted);
            leftHalf = temp;
        }        
        // Combine left and right halves
        int[] combined = combineHalves(rightHalf, leftHalf);
        
        // Apply final permutation
        int[] encryptedBits = permutation(combined, FINAL_PERMUTATION_TABLE);
        
        // Convert encrypted bits to string
        return bitsToString(encryptedBits);
    }
    

    private static String decrypt(String ciphertext, int[][] subkeys) {
        // Convert ciphertext to bits
        int[] ciphertextBits = stringToBits(ciphertext);
        
        // Apply initial permutation
        int[] permutedCiphertext = permutation(ciphertextBits, INITIAL_PERMUTATION_TABLE);
        
        // Split permuted ciphertext into halves
        int[] leftHalf = Arrays.copyOfRange(permutedCiphertext, 0, 32);
        int[] rightHalf = Arrays.copyOfRange(permutedCiphertext, 32, 64);
        
        // 16 rounds of Feistel network in reverse order
        for (int round = 15; round >= 0; round--) {
            // Implementation of Feistel network in reverse order
            
            // Save the current right half (needed for swapping)
            int[] temp = Arrays.copyOf(rightHalf, rightHalf.length);
            
            // Expansion
            int[] expandedRightHalf = expansion(rightHalf, EXPANSION_TABLE);
            
            // Key Mixing (XOR with subkey)
            int[] xorResult = xor(expandedRightHalf, subkeys[round]);
            
            // Substitution using S-boxes
            int[] substituted = substitute(xorResult);
            
            // Permutation using P-box
            int[] permuted = permutation(substituted, P_BOX);
            
            // XOR with left half
            rightHalf = xor(leftHalf, permuted);
            
            // Swap left and right halves for next round
            leftHalf = temp;
        }
        
        // Combine left and right halves
        int[] combined = combineHalves(rightHalf, leftHalf);
        
        // Apply final permutation
        int[] decryptedBits = permutation(combined, FINAL_PERMUTATION_TABLE);
        
        // Convert decrypted bits to string
        return bitsToString(decryptedBits);
    }
    
    

    // Other helper methods for permutation, expansion, substitution, etc.
    // Helper method to perform expansion
    private static int[] expansion(int[] input, int[] expansionTable) {
        int[] output = new int[expansionTable.length];
        for (int i = 0; i < expansionTable.length; i++) {
            output[i] = input[expansionTable[i] - 1];
        }
        return output;
}

// Helper method to perform XOR operation between two arrays
    private static int[] xor(int[] array1, int[] array2) {
        int[] result = new int[array1.length];
        for (int i = 0; i < array1.length; i++) {
            result[i] = array1[i] ^ array2[i];
        }
        return result;
    }

// Helper method to perform substitution using S-boxes
    private static int[] substitute(int[] input) {
        // Initialize the output array
        int[] output = new int[32];
        
        // Iterate over each 6-bit block
        for (int i = 0; i < 8; i++) {
            // Extract the current 6-bit block
            int[] block = Arrays.copyOfRange(input, i * 6, (i + 1) * 6);
            
            // Determine the row and column indices for the S-box lookup
            int row = block[0] * 2 + block[5];
            int col = block[1] * 8 + block[2] * 4 + block[3] * 2 + block[4];
            
            // Perform S-box substitution
            int value = getSBoxValue(i, row, col);
            
            // Convert the substituted value to a 4-bit binary string
            String binaryString = String.format("%4s", Integer.toBinaryString(value)).replace(' ', '0');
            
            // Store the substituted bits in the output array
            for (int j = 0; j < 4; j++) {
                output[i * 4 + j] = Character.getNumericValue(binaryString.charAt(j));
            }
        }
        
        return output;
    }
    // Helper method to retrieve the substituted value from the S-box
    private static int getSBoxValue(int sBoxIndex, int row, int col) {
        int[][][] sBoxes = {S1, S2, S3, S4, S5, S6, S7, S8};
        return sBoxes[sBoxIndex][row][col];
    }
// Helper method to perform permutation
    private static int[] permutation(int[] input, int[] permutationTable) {
        // Initialize the output array
        int[] output = new int[permutationTable.length];
        
        // Iterate over each index in the permutation table
        for (int i = 0; i < permutationTable.length; i++) {
            // Get the index from the permutation table
            int index = permutationTable[i] - 1; // Permutation tables are 1-indexed
            
            // Copy the bit from the input array to the output array at the specified index
            output[i] = input[index];
        }
        
        return output;
    }


}

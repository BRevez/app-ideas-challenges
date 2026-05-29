# Base Converter (Bin2Dec Challenge)

**Tier:** 1-Beginner

Binary is the number system all digital computers are based on. Therefore, it is important for developers to understand binary, or base 2, mathematics. The initial purpose of this project, originating from the **Bin2Dec** challenge, was to provide practice and understanding of binary calculations. 

This application has been expanded beyond the core challenge requirements. It not only allows the user to enter strings of up to 8 binary digits to display the decimal equivalent, but it also supports continuous conversions between four numerical systems (Binary, Octal, Decimal, and Hexadecimal) while outputting the mathematical calculation steps to the terminal.

url: https://github.com/florinpop17/app-ideas/blob/master/Projects/1-Beginner/Bin2Dec-App.md

## Challenge Constraints & Fulfillment

This implementation adheres strictly to the constraints provided by the original challenge:
* **Arrays may not be used to contain the binary digits entered by the user:** This application processes the input as a continuous `String` and reads it character by character using `.charAt()`.
* **Determining the decimal equivalent of a particular digit in the sequence must be calculated using a single mathematical function:** This application utilizes `Math.pow()` to calculate the positional weight of each digit.

## User Stories

* [x] User can enter up to 8 binary digits in one input field.
* [x] User must be notified if anything other than a 0 or 1 was entered. *(Expanded: The program validates appropriate ranges for Base 2, 8, 10, and 16 using Regular Expressions).*
* [x] User views the results in a single output field containing the decimal (base 10) equivalent of the binary number that was entered.

## Bonus Features

* [x] User can enter a variable number of binary digits. *(The application dynamically accepts inputs ranging from 1 to 8 characters).*

---

## Algorithm Logic Overview

To avoid creating multiple distinct functions for every possible conversion combination, this algorithm utilizes the Decimal System (Base 10) as a universal intermediary. Every conversion is consistently executed in two structural steps:

### Step 1: Any Base to Decimal (parseToDecimal)
This step employs the mathematics of Positional Notation. The algorithm reads the string from right to left (from the end to the beginning).

**Formula:** Decimal Value = Sum of (Digit * Base^Position)

**The ASCII Table Method in the Code:**
Because the input is processed as a String, the computer interprets characters rather than numerical values. In the ASCII table, the character '0' corresponds to the integer 48, '1' to 49, and so forth. To convert the character back to a standard integer, the value of '0' is subtracted:
* For numeric digits (0-9): `c - '0'` (Example: '7' - '0' = 7)
* For Hexadecimal letters (A-F): `c - 'A' + 10` (Example: 'C' is 2 positions ahead of 'A', therefore 2 + 10 = 12)

### Step 2: Decimal to Any Base (decimalToBase)
This step utilizes the mathematics of Successive Division. The algorithm takes the decimal value and repeatedly divides it by the target base until the quotient reaches zero.

**The Process:**
1. Obtain the remainder of the division using the Modulo operator (`%`).
2. The remainder is converted into a character by applying the inverse ASCII method (`remainder + '0'`).
3. Divide the decimal value by the target base for the subsequent iteration.
4. The generated remainders represent the new number in reverse order. A `.reverse()` operation is applied to the final string to output the correct digit sequence.
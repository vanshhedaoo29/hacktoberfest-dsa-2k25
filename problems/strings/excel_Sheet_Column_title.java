public class excel_Sheet_Column_title {
    public static String convertToTitle(int columnNumber) {
        StringBuilder result = new StringBuilder();
        while (columnNumber > 0) {
            columnNumber--;  // adjust for 0-based index
            result.insert(0, (char)('A' + columnNumber % 26));
            columnNumber /= 26;
        }
        return result.toString();
    }

    // 👇 main method to run the program
    public static void main(String[] args) {
        System.out.println(convertToTitle(1));   // A
        System.out.println(convertToTitle(28));  // AB
        System.out.println(convertToTitle(701)); // ZY
        System.out.println(convertToTitle(703)); // AAA
    }
}

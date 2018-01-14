import java.util.Scanner;

public class ReversePolishNotationCalculator {

    private static class StackNode {
        private StackNode underneath;
        private double data;

        public StackNode(double data, StackNode underneath) {
            this.data = data;
            this.underneath = underneath;
        }
    }

    private static class RevPolishNotation {
        private String command;
        private StackNode top;

        public RevPolishNotation(String command) {
            top = null;
            this.command = command;
        }

        public void updateValue(double new_data) {
            top = new StackNode(new_data, top);
        }

        public double extractValue( ) {
            double top_data = top.data;
            top = top.underneath;
            return top_data;
        }

        public double calculateValue( ) {
            for(int charIndex = 0; charIndex < command.length( ); charIndex++) {
                char charInPosition = command.charAt(charIndex);

                if (Character.isDigit(charInPosition)) {
                    String numberFromText = "";
                    for (int numberLength = 0; (numberLength < 100) && (Character.isDigit(command.charAt(charIndex)) || (command.charAt(charIndex) == '.')); numberLength++, charIndex++) {
                        numberFromText = numberFromText + String.valueOf(command.charAt(charIndex));
                    }
                    updateValue(Double.parseDouble(numberFromText));

                } else if("+-*/^".contains(String.valueOf(charInPosition))) {
                    addValue (charInPosition);

                } else if(charInPosition != ' ') {
                    throw new IllegalArgumentException();
                }
            }
            double calculatedValue = extractValue();
            if(!isNotMoreNumbers()) throw new IllegalArgumentException();
            return calculatedValue;
        }

        private boolean isNotMoreNumbers(){
            return top == null;
        }

        private void addValue(char charInPosition){
            double numberSecond = extractValue();
            double numberFirst = extractValue();

            if(charInPosition == '+') updateValue(numberFirst + numberSecond);
            if(charInPosition == '-') updateValue(numberFirst - numberSecond);
            if(charInPosition == '*') updateValue(numberFirst * numberSecond);
            if(charInPosition == '/') updateValue(numberFirst / numberSecond);
            if(charInPosition == '^') updateValue(Math.pow(numberFirst, numberSecond));
        }

        public static void main(String args[]) {
            while(true) {
                System.out.println("Enter RPN expression or \"quit\".");
                String RpnExpresion = new Scanner(System.in).nextLine();
                if (!calculate(RpnExpresion)) break;
            }
        }

        private static boolean calculate(String RpnExpresion){
            if(RpnExpresion.equalsIgnoreCase("quit")) return false;

            System.out.printf("Answer is %f\n", new RevPolishNotation(RpnExpresion).calculateValue());
            return true;
        }
    }
}
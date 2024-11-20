import java.util.Scanner;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.ValidationResult;
import net.objecthunter.exp4j.function.Function;

/*
 * Recomendado usar no VSCODE, caso contrario, vai ter que fazer um arquivo pox.xml para adicionar a dependencia Exp4J.
 * O Exp4J apenas calcula expressoes matematicas.
 */

/*
 * TESTAR METODO DE BISSECCAO
 *  [0.1, 1.0]                           <- intervalo
 *  ln((x^3) + x) + cos(x)               <- funcao normal
 */

/*
 * TESTAR METODO DE NEWTON
 *  x0 = 1                               <- valor inicial
 *  xcos(2x + 3)                         <- funcao normal
 *  cos (2x + 3) - 2x * sin(2x + 3)      <- funcao derivada
 */

/*
 * TESTAR METODO DAS SECANTES
 *  [0.5, 1.0]                           <- intervalo
 *  x^3 - 9x + 5                         <- funcao normal
 *  
 */

public class Main{

    static Scanner scanner = new Scanner(System.in);

    static String function = "";
    static String derivedFunction = "";

    static Function ln;

        public static void main(String[] args) {
    
            // Adicionar ln no exp4j
            ln = new Function("ln", 1) { // '1' indica que a função aceita um argumento
            @Override
            public double apply(double... args) {
                return Math.log(args[0]);
            }
            };

            System.out.println("THE PROGRAM IS CASE INSENSITIVE!");
            
            boolean buruguda = true;
            
            do {
                String options = options();

                switch (options) {
                    case "1":
                        getFunction();
                        break;
                    case "2":
                        String choice = getMethod();
                        switch (choice) {
                            case "1":
                                bisseccao();
                                break;
                            case "2":
                                newton();
                                break;
                            case "3":
                                secantes();
                                break;
                        }
                        break;
                    case "3":
                        System.exit(0);
                    default:
                        System.out.println("Resposta invalida!");
                        System.out.println("Escolha novamente: ");
                        continue;
                }
            } while (buruguda);

            scanner.close();

    }

    public static String options(){
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("Escolha o que deseja fazer: ");
        System.out.println("1) Definir funcao");
        System.out.println("2) Escolher metodo");
        System.out.println("3) Sair");
        return scanner.nextLine();
    }

    public static void getFunction(){

        System.out.print("Escreva a funcao: ");

        while (true) {
            function = scanner.nextLine().toLowerCase();

            Expression expression = new ExpressionBuilder(function.replaceAll("x", "2")).function(ln).build();

            ValidationResult vResult = expression.validate();
            if (vResult.isValid()) {
                break;
            } else{
                System.err.println("Erro!");
                for (String string : vResult.getErrors()) {
                    System.out.println(string);
                }
                System.out.print("Entre com a funcao novamente: ");
            }
        }

    }

    public static String getMethod(){
        System.out.println("Qual metodo voce deseja utilizar: ");

        System.out.println("1) Metodo da Bisseccao");
        System.out.println("2) Metodo de Newton");
        System.out.println("3) Metodo das Secantes");

        while (true) {
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                case "2":
                case "3":
                    return choice;
                default:
                    System.out.println("Resposta invalida!");
                    System.out.print("Entre com o valor novamente: ");
                    break;
            }
        }

    }

    @Deprecated
    public boolean canContinue(){
        while (true) {
            System.out.print("Deseja continuar? (s|n) (sim|nao): ");

            String continuar = scanner.nextLine();

            switch (continuar.toLowerCase()) {
                case "s":
                case "sim":
                    return true;
                case "n":
                case "nao":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Resposta invalida!");
                    continue;
            }
        }
    }

    public static double calculateFunction(double x){

        Expression expression = new ExpressionBuilder(function).function(ln).variable("x").build().setVariable("x", x);

        return expression.evaluate();
    }

    public static double calculateDerivedFunction(double x){

        Expression expression = new ExpressionBuilder(derivedFunction).function(ln).variable("x").build().setVariable("x", x);

        return expression.evaluate();
    }

    public static void bisseccao(){
        if(function.isBlank()){
            getFunction();
        }
        System.out.println("No intervalo [a, b],");
        System.out.print("Entre com o valor a: ");
        double a = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Entre com o valor b: ");
        double b = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("O erro devera ser inferior a (Entre com o valor em decimal): ");

        double erro = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("--------------------------------------------------------------------------------");

        boolean functionAIsNegative;
        boolean functionXIsNegative;

        int i = 1;

        while (true) {

            System.out.println("Intervalo [" + a + ", " + b + "]");

            System.out.println();

            double fA = calculateFunction(a);

            System.out.println("f(" + a + ") = " + fA);

            functionAIsNegative = fA < 0f ? true : false;
            
            double fB = calculateFunction(b);
            
            System.out.println("f(" + b + ") = " + fB);

            System.out.println();
            
            if(fA * fB > 0f){
                System.out.println("Fim! f(a) * f(b) > 0 !");
                return;
            }

            System.out.println("X = (" + a + " + " + b + ") / 2");

            double x = (a + b) / 2;

            System.out.println("--------------------------------------------------------------------------------");

            System.out.println("X = " + x);

            double fX = calculateFunction(x);

            System.out.println("f(" + x + ") = " + fX);

            System.out.println("--------------------------------------------------------------------------------");

            functionXIsNegative = fX < 0f ? true : false;

            if (functionXIsNegative) {
                if (functionAIsNegative) {
                    a = x;
                } else{
                    b = x;
                }
            } else{
                if (!functionAIsNegative) {
                    a = x;
                } else{
                    b = x;
                }
            }

            if(Math.abs(fX) <= erro){
                break;
            }

            i++;

            if (i > 20) {
                break;
            }

        }

    }

    public static void newton(){
        if(function.isBlank()){
            getFunction();
        }

        System.out.print("Entre com a funcao derivada: ");

        derivedFunction = scanner.nextLine().toLowerCase();

        System.out.print("Entre com o x0: ");

        double x = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("O erro devera ser inferior a (Entre com o valor em decimal): ");

        double erro = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("--------------------------------------------------------------------------------");

        int i = 1;

        while (true) {

            double fX = calculateFunction(x);

            System.out.println("f(" + x + ") = " + fX);

            System.out.println();

            double fLX = calculateDerivedFunction(fX);

            System.out.println("f'(" + x + ") = " + fLX);

            System.out.println();

            System.out.println("x" + i + " = " + x + " - (" + fX + " / " + fLX + ")");

            x = x - (fX / fLX);

            System.out.println("--------------------------------------------------------------------------------");

            System.out.println("x" + i + " = " + x);

            System.out.println("--------------------------------------------------------------------------------");

            if(Math.abs(fX) <= erro){
                break;
            }

            i++;

            if (i > 20) {
                break;
            }
        }

    }

    public static void secantes(){
        if(function.isBlank()){
            getFunction();
        }

        System.out.println("No intervalo [a, b],");
        System.out.print("Entre com o valor a: ");
        double a = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Entre com o valor b: ");
        double b = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("O erro devera ser inferior a (Entre com o valor em decimal): ");

        double erro = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("--------------------------------------------------------------------------------");

        int i = 1;

        while (true) {

            double fXAnterior = calculateFunction(a);
            
            double fXAtual = calculateFunction(b);

            System.out.println("f(x" + (i - 1) + ") = " + fXAnterior);

            System.out.println("f(x" + i + ") = " + fXAtual);

            System.out.println("--------------------------------------------------------------------------------");

            System.out.println("x" + (i + 1) + " = ((" + fXAtual + " * (" + b + " - " + a + ")) / (" + fXAtual + " - " + fXAnterior + "))");

            double x = b - ((fXAtual * (b - a)) / (fXAtual - fXAnterior));

            double fX = calculateFunction(x);

            System.out.println("x" + (i + 1) + " = " + x);

            System.out.println("f(" + x + ") = " + fX);

            System.out.println("--------------------------------------------------------------------------------");

            a = b;

            b = x;

            if(Math.abs(fX) <= erro){
                break;
            }

            i++;

            if (i > 20) {
                break;
            }

        }

    }

}
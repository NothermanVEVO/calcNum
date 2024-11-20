import java.util.Scanner;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.ValidationResult;
import net.objecthunter.exp4j.function.Function;

public class Main{

    static Scanner scanner = new Scanner(System.in);

    static String function = "";

    static Function ln;
    
    // TESTAR METODO DE BISSECCAO, INTERVALO [0.1, 1.0]
    // ln((x^3) + x) + cos(x)

        public static void main(String[] args) {
            // System.out.println(MathExp.compile("2^-x + ln 3.14159 + (-1 + -3.2 + .2)"));
            // System.out.println(MathExp.func("2^2 / (2 + |-2|)"));
            // System.out.println(MathExp.func("2^sin2 / (2 + |-2|)       (| (  ) |) ||"));
    
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

        // Use a biblioteca Exp4j para interpretar e resolver
        Expression expression = new ExpressionBuilder(function).function(ln).variable("x").build().setVariable("x", x);

        // Avalie a expressão
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

        boolean functionAIsNegative;
        boolean functionXIsNegative;

        while (true) {

            System.out.println("Intervalo [" + a + ", " + b + "]");

            double fA = calculateFunction(a);

            System.out.println("f(" + a + ") = " + fA);

            functionAIsNegative = fA < 0f ? true : false;
            
            double fB = calculateFunction(b);
            
            System.out.println("f(" + b + ") = " + fB);
            
            if(fA * fB > 0f){
                System.out.println("Fim! f(a) * f(b) > 0 !");
                return;
            }

            System.out.println("X = (" + a + " + " + b + ") / 2");

            double x = (a + b) / 2;

            System.out.println("X = " + x);

            double fX = calculateFunction(x);

            System.out.println("f(" + x + ") = " + fX);

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

        }

    }

    public static void newton(){
        if(function.isBlank()){
            getFunction();
        }
    }

    public static void secantes(){
        if(function.isBlank()){
            getFunction();
        }
    }

}
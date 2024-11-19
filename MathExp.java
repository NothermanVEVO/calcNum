public class MathExp {

    private static final String REGEX_N_SPACE = "\\s+";
    private static final String EMPTY_BRACKETS = "(\\(\\s*\\))";
    private static final String EMPTY_MODULE = "(\\|\\s*\\|)";

    /*
     * VARIABLES
     */
    private static final String REGEX_DECIMAL = "([+-]?[0-9]+\\.[0-9]+)|([+-]?\\.[0-9]+)";
    private static final String REGEX_INTEGER = "([+-]?[0-9]+)";
    private static final String REGEX_X = "([+-]?x)";

    /*
     * OPERATORS
     */
    private static final String SUM = "(\\+)";
    private static final String SUB = "(-)";
    private static final String MUL = "(\\*)";
    private static final String DIV = "(/)";
    private static final String POW = "(\\^)"; // ???
    private static final String ROT = "(root)";
    private static final String SIN = "(sin)";
    private static final String COS = "(cos)";
    private static final String TAN = "(tan)";
    private static final String CSC = "(csc)";
    private static final String SEC = "(sec)";
    private static final String COT = "(cot)";
    private static final String LOG = "(log)";
    private static final String LNN = "(ln)";
    private static final String FAT = "(!)";

    private static final String LEFT_BRACKET = "(\\()";
    private static final String RIGHT_BRACKET = "(\\))";
    private static final String MODULE = "(\\|)";

    private static final String PI = ("(PI|pi)");
    private static final String EPS = "(E|e)";

    public static String func(String string){
        String exp = compile(string);
        if(!isBalanced(exp)){
            System.out.println(exp);
            System.out.println("Not balanced");
            return null;
        }
        System.out.println(exp);
        return "";
    }

    private static String organizeString(String string){
        String org = removeEmptyBracketsNORModule(string);
        org = org.replaceAll(REGEX_N_SPACE, " ").trim();
        return org;
    }

    private static String removeEmptyBracketsNORModule(String string){
        String previousString = "";
        do {
            previousString = string;
            string = string.replaceAll(EMPTY_BRACKETS, "");
            string = string.replaceAll(EMPTY_MODULE, "");
        } while (!string.equals(previousString));
        return string;
    }

    public static String compile(String string){
        String compiled = string.replaceAll(LEFT_BRACKET, "( ");

        compiled = compiled.replaceAll(MODULE, "| ");

        compiled = compiled.replaceAll(PI, "number "); // TODO REPLACE IT WITH THE RIGHT VALUE
        compiled = compiled.replaceAll(EPS, "number "); // TODO REPLACE IT WITH THE RIGHT VALUE

        compiled = compiled.replaceAll(REGEX_X, "x ");
        compiled = compiled.replaceAll(REGEX_DECIMAL, "number ");
        compiled = compiled.replaceAll(REGEX_INTEGER, "number ");

        compiled = compiled.replaceAll(SUM, "uOP ");
        compiled = compiled.replaceAll(SUB, "uOP ");
        compiled = compiled.replaceAll(MUL, "uOP ");
        compiled = compiled.replaceAll(DIV, "uOP ");
        compiled = compiled.replaceAll(POW, "uOP ");
        compiled = compiled.replaceAll(ROT, "uOP ");

        compiled = compiled.replaceAll(SIN, "dOP ");
        compiled = compiled.replaceAll(COS, "dOP ");
        compiled = compiled.replaceAll(TAN, "dOP ");
        compiled = compiled.replaceAll(CSC, "dOP ");
        compiled = compiled.replaceAll(SEC, "dOP ");
        compiled = compiled.replaceAll(COT, "dOP ");
        compiled = compiled.replaceAll(LOG, "dOP ");
        compiled = compiled.replaceAll(LNN, "dOP ");

        compiled = compiled.replaceAll(FAT, "dpOP ");
        return organizeString(compiled);
    }

    private static boolean isBalanced(String string){
        int nLeftBrackets = 0;
        int nRightBrackets = 0;
        int nModules = 0;
        char[] array = string.toCharArray();
        for (int i = 0; i < array.length; i++) {
            switch (array[i]) {
                case '(':
                    nLeftBrackets++;
                    break;
                case ')':
                    nRightBrackets++;
                    break;
                case '|':
                    nModules++;
                    break;
            }
        }
        if(nLeftBrackets != nRightBrackets | nModules % 2 != 0){
            return false;
        }
        return true;
    }

}

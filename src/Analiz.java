import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Analiz {

    private static final String[] VALID_COMMANDS = {"TOP", "CIK", "CARP", "BOL"};

    private static boolean lexicalAnalysis(String line, int lineNumber) {
        System.out.println("Lexical analiz yapılıyor. Satır " + lineNumber + ": " + line);

        String command = "";
        int i = 0;

        // Komutu al
        while (i < line.length() && line.charAt(i) != ' ') {
            command += line.charAt(i);
            i++;
        }

        if (!isValidCommand(command)) {
            System.out.println("Geçersiz komut. Satır " + lineNumber);
            return false;
        }

        // Boşlukları geç
        while (i < line.length() && line.charAt(i) == ' ') {
            i++;
        }

        String op1 = "";
        // İlk operandı al
        while (i < line.length() && line.charAt(i) != ',') {
            op1 += line.charAt(i);
            i++;
        }

        if (!isInteger(op1)) {
            System.out.println("Lexical analiz hatası: İlk operand geçerli değil. Satır " + lineNumber);
            return false;
        }

        // Virgülü geç
        if (i < line.length() && line.charAt(i) == ',') {
            i++;
        } else {
            System.out.println("Lexical analiz hatası: Operandlar yanlış formatta. Satır " + lineNumber);
            return false;
        }

        String op2 = "";
        // İkinci operandı al
        while (i < line.length()) {
            op2 += line.charAt(i);
            i++;
        }

        if (!isInteger(op2)) {
            System.out.println("Lexical analiz hatası: İkinci operand geçerli değil. Satır " + lineNumber);
            return false;
        }

        return true;
    }

    private static boolean syntaxAnalysis(String line, int lineNumber) {
        System.out.println("Syntax analiz yapılıyor. Satır " + lineNumber + ": " + line);
        return checkSyntax(line);
    }

    private static boolean checkSyntax(String line) {
        String command = "";
        int i = 0;

        // Komutu al
        while (i < line.length() && line.charAt(i) != ' ') {
            command += line.charAt(i);
            i++;
        }

        if (!isValidCommand(command)) {
            return false;
        }

        // Boşlukları geç
        while (i < line.length() && line.charAt(i) == ' ') {
            i++;
        }

        String op1 = "";
        // İlk operandı al
        while (i < line.length() && line.charAt(i) != ',') {
            op1 += line.charAt(i);
            i++;
        }

        if (!isInteger(op1)) {
            return false;
        }

        // Virgülü geç
        if (i < line.length() && line.charAt(i) == ',') {
            i++;
        } else {
            return false;
        }

        String op2 = "";
        // İkinci operandı al
        while (i < line.length()) {
            op2 += line.charAt(i);
            i++;
        }

        return isInteger(op2);
    }

    private static boolean isValidCommand(String command) {
        for (String validCommand : VALID_COMMANDS) {
            if (validCommand.equals(command)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isInteger(String operand) {
        if (operand.isEmpty()) return false;

        for (int i = 0; i < operand.length(); i++) {
            if (i == 0 && operand.charAt(i) == '-') {
                continue;
            }
            if (operand.charAt(i) < '0' || operand.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

    private static void manualStackTrace(Exception e) {
        System.out.println("Hata: " + e.getMessage());
        System.out.println("Yığındaki çağrılar:");
        StackTraceElement[] trace = e.getStackTrace();
        for (StackTraceElement element : trace) {
            System.out.println("  at " + element.getClassName() + "." + element.getMethodName() +"(" + element.getFileName() + ":" + element.getLineNumber() + ")");
        }
        }

public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Giriş dosyasının adını girin: ");
    String fileName = scanner.nextLine();
    scanner.close();

    boolean hasLexicalError = false;
    boolean hasSyntaxError = false;

    BufferedReader reader = null;
    try {
        reader = new BufferedReader(new FileReader(fileName));
        String line;
        int lineNumber = 1;

        while ((line = reader.readLine()) != null) {
            if (!lexicalAnalysis(line, lineNumber)) {
                hasLexicalError = true;
            }

            if (!hasLexicalError && !syntaxAnalysis(line, lineNumber)) {
                System.out.println("Syntax hatası var. Satır " + lineNumber + ": " + line.trim());
                hasSyntaxError = true;
            }
            lineNumber++;
        }

        if (!hasLexicalError) {
            System.out.println("Lexical analiz tamamlandı, hata yok.");
        } else {
            System.out.println("Lexical analiz sırasında hata bulundu.");
        }

        if (!hasSyntaxError) {
            System.out.println("Syntax analiz tamamlandı, hata yok.");
        } else {
            System.out.println("Syntax analiz sırasında hata bulundu.");
        }

    } catch (IOException e) {
        System.out.println("Dosya bulunamadı.");
        manualStackTrace(e);
    } finally {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                manualStackTrace(e);
            }
        }
    }
}
}
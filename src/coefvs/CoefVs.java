package coefvs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.*;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import static oracle.jrockit.jfr.events.Bits.doubleValue;

public class CoefVs {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("ITERATIVO");
        BigInteger ti = BigInteger.ZERO;
        BigInteger tI[] = new BigInteger[1000];
        BigInteger tR[] = new BigInteger[1000];
        BigInteger coef, e, timeprom;

        String sDir = "C:\\user";
        File f = new File(sDir);
        String ruta = "C:\\user";
        String nameFile = "Resultados.txt";
        File file = new File(ruta, nameFile);
        if (!file.exists()) {
            f.mkdir();
            try {
                file.createNewFile();
            } catch (IOException ex) {
                System.out.println("Error en la creaciòn del file");
                System.exit(0);
            }
        }
        BufferedWriter auxbw;
        try {
            auxbw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
            auxbw.write("");
            auxbw.close();
        } catch (IOException ex) {
            Logger.getLogger(CoefVs.class.getName()).log(Level.SEVERE, null, ex);
        }

        e = BigInteger.valueOf(31);
        coef = BigInteger.ONE;
        try (FileWriter fw = new FileWriter(file.getAbsoluteFile(), true); BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write("Resultados No Recursivo");
            bw.newLine();
            bw.flush();
        } catch (IOException ex) {
            Logger.getLogger(CoefVs.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (BigInteger i = BigInteger.valueOf(1); i.compareTo(e) <= 0; i = i.add(BigInteger.ONE)) {
            ti = BigInteger.valueOf(System.nanoTime());
            coef = coefi(BigInteger.valueOf(35), i);
            System.out.println("Coef de " + i + " Resultado " + coef);
            tI[i.intValue()] = BigInteger.valueOf(System.nanoTime()).subtract(ti);
            System.out.println("Tiempo de " + i + ": " + tI[i.intValue()]);
            try (FileWriter fw = new FileWriter(file.getAbsoluteFile(), true); BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write("Coef de " + i + " Resultado " + coef);
                bw.newLine();
                bw.write("Tiempo de " + i + ": " + tI[i.intValue()]);
                bw.newLine();
                bw.flush();
            } catch (IOException ex) {
                Logger.getLogger(CoefVs.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        BigInteger suma = BigInteger.ZERO;
        for (int i = 1; i <= e.intValue(); i++) {
            suma = suma.add(tI[i]);
        }
        System.out.println("");
        timeprom = suma.divide(BigInteger.valueOf(e.intValue()));
        System.out.println("Promedio de tiempos " + timeprom);
        System.out.println("Desviación de tiempos " + desv(tI));
        System.out.println("");
        System.out.println("RECURSIVO");
        try (FileWriter fw = new FileWriter(file.getAbsoluteFile(), true); BufferedWriter bw = new BufferedWriter(fw)) {
            bw.newLine();
            bw.write("Resultados Recursivo");
            bw.newLine();
            bw.flush();
        } catch (IOException ex) {
            Logger.getLogger(CoefVs.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (BigInteger i = BigInteger.valueOf(1); i.compareTo(e) <= 0; i = i.add(BigInteger.ONE)) {
            ti = BigInteger.valueOf(System.nanoTime());
            coef = coefiR(BigInteger.valueOf(35), i);
            System.out.println("Coef de " + i + " Resultado " + coef);
            tR[i.intValue()] = BigInteger.valueOf(System.nanoTime()).subtract(ti);
            System.out.println("Tiempo de " + i + ": " + tR[i.intValue()]);
            System.out.println("");
            try (FileWriter fw = new FileWriter(file.getAbsoluteFile(), true); BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write("Coef de " + i + " Resultado " + coef);
                bw.newLine();
                bw.write("Tiempo de " + i + ": " + tR[i.intValue()]);
                bw.newLine();
                bw.flush();
            } catch (IOException ex) {
                Logger.getLogger(CoefVs.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        suma = BigInteger.ZERO;
        for (int i = 1; i <= e.intValue(); i++) {
            suma = suma.add(tI[i]);
        }
        System.out.println("");
        timeprom = suma.divide(BigInteger.valueOf(e.intValue()));
        System.out.println("Promedio de tiempos " + timeprom);
        System.out.println("Desviación de tiempos " + desv(tR));
    }

    public static BigInteger coefiR(BigInteger n, BigInteger k) {
        if (k.compareTo(BigInteger.ZERO) == 0 || k.compareTo(n) == 0) {
            return BigInteger.ONE;
        }

        return coefiR(n.subtract(BigInteger.ONE), k.subtract(BigInteger.ONE)).add(coefiR(n.subtract(BigInteger.ONE), k));
    }

    public static BigInteger coefi(BigInteger n, BigInteger k) {
        BigInteger resul = BigInteger.ZERO;
        resul = factorialNR(n).divide(factorialNR(k).multiply(factorialNR(n.subtract(k))));
        return resul;
    }

    public static BigInteger factorialNR(BigInteger numero) {

        BigInteger factorial;
        factorial = BigInteger.ONE;
        while (numero.compareTo(BigInteger.ZERO) > 0) {
            factorial = factorial.multiply(numero);
            numero = numero.subtract(BigInteger.valueOf(1));
        }
        return factorial;
    }

    public static BigInteger desv(BigInteger arr[]) {
        BigInteger sum = BigInteger.ZERO, sd = BigInteger.ZERO;
        BigInteger length = BigInteger.valueOf(arr.length);

        for (int i = 1; i <= 31; i++) {
            sum = sum.add(arr[i]);
        }

        BigInteger mean = sum.divide(length);
        BigInteger pow = BigInteger.ZERO;

        for (int i = 1; i <= 31; i++) {
            pow = arr[i].subtract(mean);
            sd = sd.add(pow.pow(2));
        }

        BigInteger resul;
        resul = sd.divide(length);
        double aux;
        aux = doubleValue(resul);
        aux = Math.sqrt(aux);
        long aux2;
        aux2 = (long) aux;
        resul = BigInteger.valueOf(aux2);
        return resul;
    }
}

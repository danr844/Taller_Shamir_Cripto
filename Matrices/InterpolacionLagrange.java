package Matrices;

import java.util.Scanner;

public class InterpolacionLagrange {
    private static int MODULO;

    // Método para calcular los coeficientes del polinomio de interpolación de Lagrange
    public static int[] lagrangeCoeffs(int[] x, int[] y) {
        int n = x.length;
        int[] coeffs = new int[n];

        // Inicializar los coeficientes a cero
        for (int i = 0; i < n; i++) {
            coeffs[i] = 0;
        }

        // Calcula los coeficientes del polinomio de interpolación
        for (int i = 0; i < n; i++) {
            int[] lagrangePolynomial = new int[n];
            lagrangePolynomial[0] = y[i];
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    // Calcula el denominador e inverso modular
                    int denominator = modularInverse((x[i] - x[j] + MODULO) % MODULO, MODULO);

                    // Desplazamiento del polinomio de Lagrange
                    for (int k = n - 1; k > 0; k--) {
                        lagrangePolynomial[k] = (lagrangePolynomial[k] * (-x[j]) % MODULO + lagrangePolynomial[k - 1]) % MODULO;
                    }
                    lagrangePolynomial[0] = lagrangePolynomial[0] * (-x[j]) % MODULO;

                    // Calcula el inverso modular
                    for (int k = 0; k < n; k++) {
                        lagrangePolynomial[k] = lagrangePolynomial[k] * denominator % MODULO;
                    }
                }
            }

            // Suma los términos de Lagrange al resultado
            for (int k = 0; k < n; k++) {
                coeffs[k] = (coeffs[k] + lagrangePolynomial[k]) % MODULO;
            }
        }

        return coeffs;
    }

    // Método para calcular el inverso modular utilizando el algoritmo de extensión de Euclides (EXTENDIDO)
    public static int modularInverse(int a, int m) {
        int m0 = m, t, q;
        int x0 = 0, x1 = 1;

        if (m == 1) {
            return 0;
        }

        while (a > 1) {
            q = a / m;
            t = m;

            m = a % m;
            a = t;
            t = x0;

            x0 = x1 - q * x0;
            x1 = t;
        }

        if (x1 < 0) {
            x1 += m0;
        }

        return x1;
    }

    // Método para calcular el valor interpolado en xValue usando los coeficientes del polinomio de interpolación
    public static int lagrangeInterpolacion(int[] coeffs, int xValue) {
        int resultado = 0;
        int n = coeffs.length;

        // Calcular el valor interpolado usando los coeficientes
        for (int i = 0; i < n; i++) {
            int term = coeffs[i];
            // Calcular xValue^i mod MODULO
            int xValuePow = 1;
            for (int j = 0; j < i; j++) {
                xValuePow = (xValuePow * xValue) % MODULO;
            }
            term = (term * xValuePow) % MODULO;
            resultado = (resultado + term) % MODULO;
        }

        return resultado;
    }

    public static void main(String[] args) {
        // Crear un objeto Scanner para leer la entrada del usuario
        Scanner scanner = new Scanner(System.in);

        // Leer el módulo desde la entrada del usuario
        System.out.println("\nIngrese el módulo: ");
        MODULO = scanner.nextInt();

        // Leer la longitud de los arreglos x y y
        System.out.println("Ingrese la longitud de los arreglos x y y: ");
        int n = scanner.nextInt();

        // Inicializar los arreglos x y y
        int[] x = new int[n];
        int[] y = new int[n];

        // Leer los valores del arreglo x
        System.out.println("Ingrese los valores de x:");
        for (int i = 0; i < n; i++) {
            System.out.print("x[" + i + "]: ");
            x[i] = scanner.nextInt();
        }

        // Leer los valores del arreglo y
        System.out.println("Ingrese los valores de y:");
        for (int i = 0; i < n; i++) {
            System.out.print("y[" + i + "]: ");
            y[i] = scanner.nextInt();
        }

        // Calcular los coeficientes del polinomio de interpolación de Lagrange
        int[] coeffs = lagrangeCoeffs(x, y);

        // Mostrar los coeficientes encontrados
        System.out.println("Coeficientes del polinomio de interpolación de Lagrange:");
        for (int i = 0; i < coeffs.length; i++) {
            if (coeffs[i] <0){
                System.out.println("\na" + i + " = " + (coeffs[i]+MODULO));
            }
            else{
                System.out.println("\na" + i + " = " + coeffs[i]);
            }
        }

        // Leer el valor de x para la interpolación
        System.out.println("Ingrese el valor de x para calcular la interpolación: ");
        int xValue = scanner.nextInt();

        // Calcular el valor interpolado en xValue usando los coeficientes
        int interpolacion = lagrangeInterpolacion(coeffs, xValue);

        // Mostrar el resultado de la interpolación
        if (interpolacion < 0){
            System.out.println("El valor interpolado en x = " + xValue + " es: " + (interpolacion+MODULO));
        }
        else{
            System.out.println("El valor interpolado en x = " + xValue + " es: " + (interpolacion));
        }
        // Cerrar el scanner
        scanner.close();
    }
}

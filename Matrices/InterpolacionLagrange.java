package Matrices;

public class InterpolacionLagrange {
    private static final int MODULO = 1301;

    // Método para calcular los coeficientes del polinomio de interpolación de Lagrange mod 1301
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

    // Método para calcular el inverso modular utilizando el algoritmo de extensión de Euclides
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
            // Calcular xValue^i mod 1301
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
        // Puntos de datos
        int[] x = {4, 3, 8, 1}; // Puntos x
        int[] y = {2, 1, 10, 360}; // Puntos y

        // Calcular los coeficientes del polinomio de interpolación de Lagrange mod 1301
        int[] coeffs = lagrangeCoeffs(x, y);

        // Mostrar los coeficientes encontrados
        System.out.println("Coeficientes del polinomio de interpolación de Lagrange mod 1301:");
        for (int i = 0; i < coeffs.length; i++) {
            if (coeffs[i] < 0){
                System.out.println("a" + i + " = " + (coeffs[i] + MODULO));
        }
            else {
            System.out.println("a" + i + " = " + coeffs[i]);
            }
        }

        // Valor de x para el que se desea encontrar el valor interpolado
        int xValue = 0;

        // Calcular el valor interpolado en xValue usando los coeficientes
        int interpolacion = lagrangeInterpolacion(coeffs, xValue);
        if (interpolacion < 0){
            System.out.println(" El valor interpolado (secreto) es: " + (interpolacion + MODULO));
    }
        else {
            System.out.println(" El valor interpolado (secreto) es: " + interpolacion);
        }
        // Mostrar el resultado de la interpolación
    }
}

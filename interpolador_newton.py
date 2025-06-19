import sympy as sp
import numpy as np

def interp_newton(x, y):
    n = len(x)
    
    a = y.copy()
    
    for j in range(1, n):
        
        for i in range(n-1, j-1, -1):
            
            a[i] = (a[i] - a[i-1]) / (x[i] - x[i-j])

    def p(z):
        val = a[n-1]
        for i in range(n-2, -1, -1):
            val = val * (z - x[i]) + a[i]
        return val

    return p

x_str = input('array x, (usa espaço para separar números, senão dá pani.): ').strip().split()
y_str = input('array y, (usa espaço para separar números, senão dá pani.): ').strip().split()

est = input('F(x) (usa espaço para separar números, senão dá pani.): ')

x = np.array(x_str, dtype=int)
y = np.array(y_str, dtype=int)

P = interp_newton(x, y)

x_sym = sp.symbols('x')
polinomio = sp.poly(P(x_sym), x_sym)

print(f"\nPolinômio interpolador de Newton:")
print(sp.simplify(str(polinomio.as_expr())))

x = sp.Symbol('x')

print(f"\nResultado estimativa f({est}):")
print(sp.simplify(polinomio.subs(x, est)))
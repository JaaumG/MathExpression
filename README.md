# Math Expression

A java library capable of parsing and evaluating mathematical expressions.

## Features

- [x] Basic arithmetic operations
- [X] Trigonometric functions
- [X] Logarithmic functions
- [X] Exponential functions
- [ ] Calculus
- [ ] Complex numbers
- [ ] Vectors
- [ ] Matrices

## Usage

### Basic arithmetic operations

```java
import dev.joao_guilherme.Expression;

public class Main {
    public static void main(String[] args) {
        Expression expression = new Expression("2+2"); // 2 + 2
        System.out.println(expression.evaluate());
    }
}
```

### Trigonometric functions

```java
import dev.joao_guilherme.Expression;

public class Main {
    public static void main(String[] args) {
        Expression expression = new Expression("sin(pi/2)"); // sin(π/2)
        System.out.println(expression.evaluate());
    }
}
```

### Logarithmic functions

```java
import dev.joao_guilherme.Expression;

public class Main {
    public static void main(String[] args) {
        Expression expression = new Expression("log(10, 2)"); // log₂(10)
        System.out.println(expression.evaluate());
    }
}
```

### Exponential functions

```java
import dev.joao_guilherme.Expression;

public class Main {
    public static void main(String[] args) {
        Expression expression = new Expression("2^3"); // 2³
        System.out.println(expression.evaluate());
    }
}
```

### Declaring variables

```java
import dev.joao_guilherme.Expression;

public class Main {
    public static void main(String[] args) {
        Expression expression = new Expression("x^2"); // x²
        expression.withVariable("x", 2);
        System.out.println(expression.evaluate());
    }
}
```

### Declaring functions

```java
import dev.joao_guilherme.Expression;

public class Main {
    public static void main(String[] args) {
        Expression expression = new Expression("sum(1, 2)"); // 1 + 2
        expression.withFunction("sum", (values) -> values[0] + values[1]);
        System.out.println(expression.evaluate());
    }
}
```

### Using constants

```java
import dev.joao_guilherme.Expression;

public class Main {
    public static void main(String[] args) {
        Expression expression = new Expression("pi"); // π
        System.out.println(expression.evaluate());
    }
}
```

### Based percentage addition

```java
import dev.joao_guilherme.Expression;
import dev.joao_guilherme.evaluators.PercentageBasedAdditionEvaluator;

public class Main {
    public static void main(String[] args) {
        Expression expression = new Expression("2000+50%", new PercentageBasedAdditionEvaluator()); // 2000 + 50% of 2000
        System.out.println(expression.evaluate());
    }
}
```

### Equation Evaluator

This feature allows you to solve equations for a given variable.
```java
import dev.joao_guilherme.Expression;

public class Main {
    public static void main(String[] args) {
        Expression equation = new Expression("2^x = 16");
        System.out.println(equation.solveForX()); // x = 4
    }
}
```
**This feature is still in beta and may not work as expected.**

## Included functions

- `sqrt(x)`: Returns the square root of `x`.
- `nrt(n, x)`: Returns the `n`-th root of `x`.
- `log(x, base)`: Returns the logarithm of `x` in the base `base`.
- `ln(x)`: Returns the natural logarithm of `x`.
- `exp(x)`: Returns the exponential of `x`.
- `sin(x)`: Returns the sine of `x`.
- `cos(x)`: Returns the cosine of `x`.
- `tan(x)`: Returns the tangent of `x`.
- `abs(x)`: Returns the absolute value of `x`.
- `ceil(x)`: Returns the smallest integer greater than or equal to `x`.
- `floor(x)`: Returns the largest integer less than or equal to `x`.

## Included constants

- `pi`: The mathematical constant π.
- `e`: The mathematical constant e.

## How to install

### Maven

```xml
<dependency>
    <groupId>dev.joao_guilherme</groupId>
    <artifactId>math-expression</artifactId>
    <version>3.1.0</version>
</dependency>
```
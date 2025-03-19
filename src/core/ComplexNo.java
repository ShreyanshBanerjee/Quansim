package quansim.core;

public class ComplexNo {
	double a;
	double b;

	public ComplexNo(double a, double b) {
		this.a = a;
		this.b = b;
	}

	public ComplexNo(double a) {
		this.a = a;
		this.b = 0;
	}

	public static ComplexNo fromExp(double theta) {
		return new ComplexNo(Math.cos(theta), Math.sin(theta));
	}

	public boolean isImaginary() {
		return (b != 0);
	}

	public double getVal() {
		if (this.isImaginary()) {
			System.out.println("Warning: ComplexNo.getVal() was called on an imaginary number. Returning only real part.");
		}
		return this.a;
	}

	public void display() {
		if (this.isImaginary()) {
			System.out.println(a+" "+b+"i");
		} else {
			System.out.println(a);
		}
	}

	public ComplexNo add(ComplexNo second) {
		return new ComplexNo(this.a+second.a, this.b+second.b);
	}

	public ComplexNo sub(ComplexNo second) {
		return new ComplexNo(this.a-second.a, this.b-second.b);
	}

	public ComplexNo mult(ComplexNo second) {
		return new ComplexNo(this.a * second.a - this.b * second.b, this.a * second.b + this.b * second.a);
	}

	public ComplexNo div(ComplexNo second) {
		if (second.a == second.b || second.a == -second.b) {
			System.out.println("Error: ComplexNo.div() requires the second value to satisfy the precondition |a| != |b|");
		}
		double divisor = Math.pow(second.a, 2) - Math.pow(second.b, 2);

		return new ComplexNo((this.a * second.a - this.b * second.b) / divisor, (this.a * second.b + this.b * second.a) / divisor);
	}

	public ComplexNo conjugate() {
		return new ComplexNo(this.a, -this.b);
	}

	public double modSq() {
		return this.a * this.a + this.b * this.b;
	}

	public double mod() {
		return Math.sqrt(this.modSq());
	}

	public double arg() {
		return Math.atan(b / a);
	}
}

package number.natural;

import number.nothing.NaN;

public interface Even extends Natural {

	Natural half();
	
	default Increment increment() {
		return Increment.of(this);
	}
	
	Even add(Even that);
	Double add(Double that);
	Increment add(Increment that);
	
	default Positive add(Positive that) {
		return that.accept(new Positive.Visitor<Positive>() {
			@Override
			public Positive handle(Double that) {
				return add(that);
			}
			@Override
			public Positive handle(Increment that) {
				return add(that);
			}
			@Override
			public Positive handle(NaN that) {
				return that;
			}
		});
	}
	
	default Even multiply(Even that) {
		return that.accept(new Even.Visitor<Even>() {
			@Override
			public Even handle(Double that) {
				return multiply(that);
			}
			@Override
			public Even handle(Zero that) {
				return multiply(that);
			}
			@Override
			public NaN handle(NaN that) {
				return that;
			}
		});
	}
	
	Even multiply(Double that);
	Even multiply(Increment that);
	
	default Natural multiply(Positive that) {
		return that.accept(new Positive.Visitor<Natural>() {
			@Override
			public Natural handle(Double that) {
				return multiply(that);
			}
			@Override
			public Natural handle(Increment that) {
				return multiply(that);
			}
			@Override
			public NaN handle(NaN that) {
				return that;
			}
		});
	}
	
	
	default <T> T accept(Natural.Visitor<T> visitor) {
		return accept((Visitor<T>)visitor);
	}
	
	<T> T accept(Visitor<T> visitor);
	
	interface Visitor<T> {
		T handle(Double that);
		T handle(Zero that);
		T handle(NaN that);
	}
	
}

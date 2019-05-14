package pepijno.jeader;

import java.util.function.Function;

public final class Reader<T,R> {
    private final Function<? super T, ? extends R> run;

    private Reader(final Function<? super T, ? extends R> run) {
        if (run == null) {
            throw new NullPointerException();
        }
        this.run = run;
    }

    public R run(final T input) {
        return this.run.apply(input);
    }

    public <R1> Reader<T, R1> map(final Function<? super R, ? extends R1> f) {
        return new Reader<>(t -> f.apply(this.run(t)));
    }

    public <R1> Reader<T, R1> flatMap(final Function<? super R, ? extends Reader<T, R1>> f) {
        return new Reader<>(t -> f.apply(this.run(t)).run(t));
    }

    public static <T, R> Reader<T, R> of(final Function<? super T, ? extends R> f) {
        return new Reader<>(f);
    }
}

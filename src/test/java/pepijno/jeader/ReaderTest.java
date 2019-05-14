package pepijno.jeader;

import org.junit.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class ReaderTest {
    @Test
    public void canCreateReaderFromOfMethod() {
        Function<Integer, String> f = Object::toString;
        Reader<Integer, String> reader = Reader.of(f);
        assertThat(reader).isInstanceOf(Reader.class);
    }

    @Test
    public void appliesReaderFunctionOnRun() {
        Function<Integer, Integer> f = i -> i * 2;
        Reader<Integer, Integer> reader = Reader.of(f);
        assertThat(reader.run(3)).isEqualTo(6);
    }

    @Test
    public void appliesMethodOnMap() {
        Function<Integer, Integer> f = i -> i * 2;
        Reader<Integer, Integer> reader = Reader.of(f);
        Function<Integer, Integer> map = i -> i - 2;
        Reader<Integer, Integer> mapped = reader.map(map);
        assertThat(mapped.run(3)).isEqualTo(4);
    }

    @Test
    public void shouldBindOnFlatMap() {
        Function<Integer, Integer> f = i -> i * 2;
        Reader<Integer, Integer> reader = Reader.of(f);
        Function<Integer, Reader<Integer, Integer>> flatMap = x -> {
            Function<Integer, Integer> f2 = i -> i * x;
            return Reader.of(f2);
        };
        Reader<Integer, Integer> flatMapped = reader.flatMap(flatMap);
        assertThat(flatMapped.run(3)).isEqualTo(18);
    }
}

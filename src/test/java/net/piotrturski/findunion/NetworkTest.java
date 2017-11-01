package net.piotrturski.findunion;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/*
 * the library is already tested so here i just quickly check if i used it correctly
 */
@RunWith(ZohhakRunner.class)
public class NetworkTest {

    Network network = new Network(6);

    @Before
    public void connectHalves() {
        network.connect(1,2);
        network.connect(3,2);

        network.connect(6,5);
        network.connect(4,5);
    }

    @Test
    public void should_detect_disconnected_nodes() throws Exception {

        boolean connected = network.query(1, 6);

        assertThat(connected).isFalse();
    }

    @TestWith({
        "1, 1",
        "1, 2",
        "2, 1",
        "1, 3",
        "4, 5",
    })
    public void should_detect_connected_nodes(int x, int y) throws Exception {

        boolean connected = network.query(x, y);

        assertThat(connected).isTrue();
    }

    @TestWith({
            "0, 2",
            "2, 0",
            "1, 7",
            "7, 1",
            "-3, 1",
    })
    public void should_reject_bad_input(int x, int y) {

        assertThatThrownBy(
                    () -> network.query(x, y)
                            ).isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(
                    () -> network.connect(x, y)
                            ).isInstanceOf(IllegalArgumentException.class);
    }

    @TestWith({
            "0",
            "-1",
    })
    public void should_reject_non_positive_network_size(int size) {

        assertThatThrownBy(
                () -> new Network(size)
                        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void should_survive_connecting_node_with_himself() {
        network.connect(1,1);

        boolean connected = network.query(1,1);

        assertThat(connected).isTrue();
    }
}
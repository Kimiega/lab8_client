package ioManager;

import java.util.Arrays;
import java.util.Queue;
import java.util.Stack;
import java.util.stream.Collectors;

public class WindowReader implements IReadable{
    private Queue<String> queue;
    public WindowReader(Queue<String> queue){
        this.queue = queue;
    }
    @Override
    public String readline() {
        return queue.poll();
    }
}

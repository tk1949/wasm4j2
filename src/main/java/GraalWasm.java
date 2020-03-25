import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.io.ByteSequence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GraalWasm
{
    public static void main(String... args) throws IOException
    {
        byte[] binary = Files.readAllBytes(Paths.get("Math.wasm"));
        String func = "sub";
        Object[] objects = new Object[]{1, 2};

        ByteSequence sequence = ByteSequence.create(binary);
        Source.Builder sourceBuilder = Source.newBuilder("wasm", sequence, func);
        Source source = sourceBuilder.build();

        Context.Builder contextBuilder = Context.newBuilder("wasm");
        Context context = contextBuilder.build();
        context.eval(source);

        Value wasm = context.getBindings("wasm");
        Value member = wasm.getMember(func);
        Value execute = member.execute(objects);
        String s = execute.toString();
        System.out.println(s);
    }
}
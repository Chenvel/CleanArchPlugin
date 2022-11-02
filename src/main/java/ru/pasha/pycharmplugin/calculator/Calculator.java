package ru.pasha.pycharmplugin.calculator;

import com.intellij.openapi.vfs.VirtualFile;
import ru.pasha.pycharmplugin.pojo.Info;
import ru.pasha.pycharmplugin.pojo.Result;

import java.util.Map;

public class Calculator {

    public Result calculate(VirtualFile file, Info info) {
        Result result = new Result();
        result.setFile(file);

        int classes = info.getClasses();
        int sumOfFan = info.getFanOut() + info.getFanIn();

        float I = (float) info.getFanOut() / (float) (sumOfFan == 0 ? 1 : sumOfFan);
        float A = (float) info.getAbs() / (float) (classes == 0 ? 1 : classes);

        result.setCord(Map.of(I, A));

        return result;
    }
}

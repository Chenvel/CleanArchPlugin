package ru.pasha.pycharmplugin.core.acyclic;

import ru.pasha.pycharmplugin.storage.ProjectInfoStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AcyclicSearcher {

    public void search() {
        Map<String, List<String>> map = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : ProjectInfoStorage.getDependedFiles().entrySet()) {
            for (String dependedFile : entry.getValue()) {
                List<String> list = ProjectInfoStorage.getDependedFiles().get(dependedFile);
                if (list != null) {
                    if (list.contains(entry.getKey())) {
                        if (!map.containsKey(entry.getKey())) {
                            map.put(entry.getKey(), new ArrayList<>());
                        }

                        map.get(entry.getKey()).add(dependedFile);
                    }
                }
            }
        }

        ProjectInfoStorage.setAcyclicDependedFiles(map);
    }
}

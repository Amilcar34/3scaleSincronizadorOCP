package app;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.google.common.base.Supplier;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;

public class CheckPipeline {

	private final String namespace;
	private Set<String> tasksSet = new HashSet<>();
	private Set<String> artefactSet = new TreeSet<>();
	private Map<String, Set<String>> artefactTask = new HashMap<>();
	private Table<String, String, Boolean> artefactTaskTable = newTable();

	private Table<String, String, Boolean> taskTestFalse = newTable();

	public CheckPipeline(String namespace) {
		this.namespace = namespace;
	}

	public void star() {
		pullTaskArtefact();
//		System.out.format(format, "  Artefacto", tasksSet);

		Map<String, Boolean> artefacttasks = new HashMap<>(tasksSet.size());
		tasksSet.forEach(t -> artefacttasks.put(t, false));

		artefactTask.forEach((artefact, tasks) -> {
			tasksSet.forEach(t -> {
				artefactTaskTable.put(artefact, t, tasks.contains(t));
			});
		});

		System.out.println();
		System.out.printf("%25s", "Artefacto | ");
		tasksSet.forEach(t -> System.out.printf("%17s", t.toUpperCase() + " | "));
		
		artefactSet.forEach(a -> {
			System.out.println();
			System.out.printf("%25s", a + " |");
			tasksSet.forEach(t -> {
				Boolean apply = artefactTaskTable.get(a, t);
				if(apply)
					System.out.printf("%17s", apply + " | ");
				else {
					String applyS = apply.toString().toUpperCase();
					System.out.printf("%17s", applyS + " | ");
					taskTestFalse.put(a, t, apply);
				}
			});
		});
		System.out.println("\n------------FIN------------");
		
		taskTestFalse();
	}

	private void taskTestFalse() {
		System.out.println("\n------------taskTestFalse------------");
		System.out.println();
		System.out.printf("%25s", "Artefacto | ");
		tasksSet.forEach(t -> System.out.printf("%17s", t.toUpperCase() + " | "));
		taskTestFalse.rowKeySet().forEach(a -> {
			System.out.println();
			System.out.printf("%25s", a + " |");
			tasksSet.forEach(t -> {
				Boolean apply = artefactTaskTable.get(a, t);
				if(apply)
					System.out.printf("%17s", apply + " | ");
				else {
					String applyS = apply.toString().toUpperCase();
					System.err.printf("%17s", applyS + " | ");
					taskTestFalse.put(a, t, apply);
				}
			});
		});
		System.out.println();
	}

	private void pullTaskArtefact() {
		String[] pipelines = Helper.getPipelineByNamespace(namespace);
		for (String pipeline : pipelines) {
			String[] tasks = Helper.getTasksByNamespaceAndPipeline(namespace, pipeline);
			Set<String> taskSet = Set.of(tasks);
			tasksSet.addAll(taskSet);
			pipeline = pipeline.replace("-cd-pipeline", "");
			pipeline = pipeline.replace("-java", "");
			pipeline = pipeline.replace("-angular", "");
			artefactSet.add(pipeline);
			artefactTask.put(pipeline, taskSet);
		}
	}

	public Table<String, String, Boolean> newTable() {
		return Tables.newCustomTable(Maps.<String, Map<String, Boolean>>newHashMap(),
				new Supplier<Map<String, Boolean>>() {
					public Map<String, Boolean> get() {
						return Maps.newHashMap();
					}
				});
	}
}

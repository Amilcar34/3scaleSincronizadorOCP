package app;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

import com.google.common.base.Supplier;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;

public class CheckStepPipeline {

	private final String namespace;
	private Set<String> tasksSet = new HashSet<>();
	private Set<String> artefactSet = new TreeSet<>();
	private Map<String, Set<String>> artefactTask = new TreeMap<>();
	private Table<String, String, Boolean> artefactTaskTable = newTable();
	private Table<String, String, Boolean> taskTestFalse = newTable();
	private final String formatColumn = "%15s";

	public CheckStepPipeline(String namespace) {
		this.namespace = namespace;
	}

	public void star() {
		pullTaskArtefact();

		Map<String, Boolean> artefacttasks = new HashMap<>(tasksSet.size());
		tasksSet.forEach(t -> artefacttasks.put(t, false));

		artefactTask.forEach((artefact, tasks) -> {
			tasksSet.forEach(t -> {
				artefactTaskTable.put(artefact, t, tasks.contains(t));
			});
		});
		System.out.println();
		System.out.println(artefactTaskTable);
		System.out.println();
		System.out.printf("%25s", "Artefacto     |");
		tasksSet.forEach(t -> System.out.printf(formatColumn, t.toUpperCase()));

		artefactSet.forEach(a -> {
			System.out.println();
			System.out.printf("%25s", a.replace("legacy", "lgy"));
			tasksSet.forEach(t -> {
				Boolean apply = artefactTaskTable.get(a, t);
				if (apply)
					System.out.printf(formatColumn, apply);
				else {
					String applyS = apply.toString().toUpperCase();
					System.out.printf(formatColumn, applyS);
					if (t.equals("test"))
						taskTestFalse.put(a, t, apply);
				}
			});
		});
		System.out.println("\n- - - - - - - - - - - - FIN - - - - - - - - - - - -\n");

		taskTestFalse();
	}

	private void taskTestFalse() {
		System.out.println("\n------------taskTestFalse------------\n");
		System.out.printf("%25s", "Artefacto   |");
		tasksSet.forEach(t -> System.out.printf(formatColumn, t.toUpperCase()));
		taskTestFalse.rowKeySet().forEach(a -> {
			System.out.println();
			System.out.printf("%25s", a);
			tasksSet.forEach(t -> {
				Boolean apply = artefactTaskTable.get(a, t);
				if (apply)
					System.out.printf(formatColumn, apply);
				else {
					String applyS = apply.toString().toUpperCase();
					System.err.printf(formatColumn, applyS);
					taskTestFalse.put(a, t, apply);
				}
			});
		});
		System.out.println("\n");
		taskTestFalse.rowKeySet().forEach(System.out::println);
		System.out.println();
	}

	private void pullTaskArtefact() {
		String[] pipelines = Helper.getPipelineByNamespace(namespace);
		Map<String, Set<String>> pipelineTasks = new HashMap<String, Set<String>>();
		for (String pipeline : pipelines) {
			String[] tasks = Helper.getTasksByNamespaceAndPipeline(namespace, pipeline);
			Set<String> taskSet = Set.of(tasks);
			pipelineTasks.put(pipeline, taskSet);
			pipeline = pipeline.replace("-pipeline", "");
			pipeline = pipeline.replace("-pipeline", "");
			pipeline = pipeline.replace("-java", "");
			pipeline = pipeline.replace("-angular", "");
			pipeline = pipeline.replace("-nodejs", "");
			artefactSet.add(pipeline);
			artefactTask.put(pipeline, taskSet);
		}

		int moreT = 0;
		for (Map.Entry<String, Set<String>> entry : pipelineTasks.entrySet()) {
			Set<String> tasks = entry.getValue();
			if (moreT < tasks.size()) {
				moreT = tasks.size();
				tasksSet = tasks;
			}
		}
		tasksSet = new HashSet<String>();
		if(checkTask(pipelineTasks))
			return;
		else
			pipelineTasks.forEach((p, t) -> tasksSet.addAll(t));
	}

	private boolean checkTask(Map<String, Set<String>> pipelineTasks) {
		for (Map.Entry<String, Set<String>> entry : pipelineTasks.entrySet()) {
			Set<String> tasks = entry.getValue();
			for (String task : tasks) {
				if(!tasksSet.contains(task))
					return false;
			}
		}
		return true;
	}

	public Table<String, String, Boolean> newTable() {
		return Tables.newCustomTable(Maps.<String, Map<String, Boolean>>newTreeMap(),
				new Supplier<Map<String, Boolean>>() {
					public Map<String, Boolean> get() {
						return Maps.newHashMap();
					}
				});
	}
}

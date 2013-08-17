class DependencyToDotLanguageTranslator {

	List<String> translateDependenciesToDotLanguage(Map<String, Set<String>> dependenciesGroupedByModule, String regex) {
		List<String> graphEntries = []

		dependenciesGroupedByModule.each { module, dependencies ->
			dependencies.each { dependency ->
				graphEntries << "\"$module\" -> \"$dependency\";"
			}
		}

		dependenciesGroupedByModule.findAll{ it.key ==~ regex }.each{ module, dependencies ->
			graphEntries << "\"$module\" [fontcolor=blue, color=blue, label=<<TABLE border=\"0\"><TR><TD title=\"$module\" href=\"javascript:redrawGraph('$module');\">$module</TD></TR></TABLE>>];"
		}


		def allDependencies = [] as Set
		dependenciesGroupedByModule.each { module, dependencies ->
			allDependencies.addAll dependencies
		}

		allDependencies.removeAll dependenciesGroupedByModule.findAll{ it.key ==~ regex }.keySet()

		allDependencies.each { dependency ->
			graphEntries << "\"$dependency\" [label=<<TABLE border=\"0\"><TR><TD title=\"$dependency\" href=\"javascript:redrawGraph('$dependency');\">$dependency</TD></TR></TABLE>>];"
		}

		return graphEntries
	}
}
# To do

- [x] Implement execution data structures (translate from python prototype)
- [x] Implement node content behaviour (a support for later operation implementations) inside the custom types
- [ ] Implement parser (translate from python prototype)
- [ ] Test execution data structures and type operations
- [ ] Implement interpreter loop calling element node at each step
- [ ] Implement operations INSIDE INTERPRETER
- [ ] remove mainlen and put ismainempty

# Notes

- Implement node execution + enzyme behavior in Interpreter itself, not in nodes or other classes.
- Make every execution structure a field of Interpreter instances.
- Don't make interpreter a singleton.
- Rename both Github repo and IntelliJ's as Polymeric-language.
- The blog post could be titled "Meet the Polymeric language: an exotic model of computation".
- remove time operation, use epochsecond and nanos.
- remove rand operation.
- add append operation to files.
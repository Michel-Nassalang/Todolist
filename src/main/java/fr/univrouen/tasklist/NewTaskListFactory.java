package fr.univrouen.tasklist;

public class NewTaskListFactory implements TaskListFactory {
        @Override
        public TaskList createTaskList() {
            return new TaskList();
        }
}

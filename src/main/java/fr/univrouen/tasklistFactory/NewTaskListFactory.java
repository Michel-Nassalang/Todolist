package fr.univrouen.tasklistFactory;

import fr.univrouen.tasklistObserver.TaskList;

public class NewTaskListFactory implements TaskListFactory {
        @Override
        public TaskList createTaskList() {
            return new TaskList();
        }
}

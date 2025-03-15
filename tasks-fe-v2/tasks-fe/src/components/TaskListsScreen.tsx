import { Button, Card, CardBody, Progress } from "@nextui-org/react";
import { List, Plus } from "lucide-react";
import React, { useEffect, useMemo } from "react";
import { useNavigate } from "react-router-dom";
import { useAppContext } from "../AppProvider";
import { TaskStatus } from "../domain/TaskStatus";

const TaskListScreen: React.FC = () => {
  const { state, api } = useAppContext();

  // Fetch task lists when the component mounts
  useEffect(() => {
    const fetchData = async () => {
      if (null == state.taskLists) {
        await api.fetchTaskLists();
      }
      // Fetch tasks for each task list
      if (state.taskLists) {
        for (const list of state.taskLists) {
          if (list.id && !state.tasks[list.id]) {
            await api.fetchTasks(list.id);
          }
        }
      }
    };
    fetchData();
  }, [state.taskLists?.length]);

  // Calculate progress for each task list
  const getTaskListProgress = (taskListId: string | undefined) => {
    if (!taskListId || !state.tasks[taskListId]) {
      return 0;
    }
    const tasks = state.tasks[taskListId];
    const closedTasks = tasks.filter(
      (task) => task.status === TaskStatus.CLOSED
    ).length;
    return tasks.length > 0 ? closedTasks / tasks.length : 0;
  };

  // Get a handle on the router
  const navigate = useNavigate();

  const handleCreateTaskList = () => {
    navigate("/new-task-list");
  };

  const handleSelectTaskList = (taskListId: string | undefined) => {
    navigate(`/task-lists/${taskListId}`);
    console.log(`Navigating to task list ${taskListId}`);
  };

  return (
    <div className="p-4 max-w-sm w-full">
      <h1 className="text-2xl font-bold mb-4 pr-2">My Task Lists</h1>
      <Button
        onPress={handleCreateTaskList}
        color="primary"
        startContent={<Plus size={20} aria-hidden="true" />}
        className="w-full mb-4"
        aria-label="Create New Task List"
      >
        Create New Task List
      </Button>
      {/* console.log("Task Lists:", state.taskLists);             //Added this line to check the log */}
      {state.taskLists.map((list) => {
        const progress = getTaskListProgress(list.id);
        const taskCount = list.id && state.tasks[list.id] ? state.tasks[list.id].length : 0;
        return (
          <Card
            key={list.id}
            isPressable
            onPress={() => handleSelectTaskList(list.id)}
            className="mb-4 w-full"
            role="button"
            aria-label={`Select task list: ${list.title}`}
          >
            <CardBody>
              <div className="flex items-center">
                <List
                  size={20}
                  className="mr-2 opacity-[40%]"
                  aria-hidden="true"
                />
                <h2 className="text-lg font-semibold">{list.title}</h2>{" "}
              </div>
              <p className="text-sm text-gray-500 mt-2">{taskCount} tasks</p>
              <Progress
                value={progress * 100}
                className="mt-2"
                color="primary"
                aria-label={`Progress for ${list.title}: ${Math.round(progress * 100)}%`}
              />
            </CardBody>
          </Card>
        );
      })}
    </div>
  );
};

export default TaskListScreen;

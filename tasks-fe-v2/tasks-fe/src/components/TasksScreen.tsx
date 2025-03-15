import { parseDate } from "@internationalized/date";
import {
  Button,
  Checkbox,
  DateInput,
  Progress,
  Spacer,
  Table,
  TableBody,
  TableCell,
  TableColumn,
  TableHeader,
  TableRow,
  Spinner,
} from "@nextui-org/react";
import { ArrowLeft, Edit, Minus, Plus, Trash } from "lucide-react";
import React, { useEffect, useState } from "react";
import { useNavigate, useParams, useLocation } from "react-router-dom";
import { useAppContext } from "../AppProvider";
import Task from "../domain/Task";
import { TaskStatus } from "../domain/TaskStatus";

const TaskListScreen: React.FC = () => {
  const { state, api } = useAppContext();
  const { listId } = useParams();
  const [isLoading, setIsLoading] = useState(true);
  const navigate = useNavigate();
  const location = useLocation();

  // Handle browser back button
  useEffect(() => {
    const handlePopState = () => {
      // If we're on a task list page and going back, ensure we go to the main page
      if (location.pathname.startsWith('/task-lists/')) {
        navigate('/', { replace: true });
      }
    };

    window.addEventListener('popstate', handlePopState);
    return () => window.removeEventListener('popstate', handlePopState);
  }, [location]);

  console.log("🔍 TaskListScreen rendered with listId:", listId);
  console.log("📌 Current state.taskLists:", state.taskLists);

  const taskList = state.taskLists.find((tl) => listId === tl.id);
  console.log("📌 Found taskList:", taskList);

  useEffect(() => {
    const loadInitialData = async () => {
      if (!listId) {
        console.warn("⚠️ No listId provided, skipping fetch");
        return;
      }

      setIsLoading(true);
      try {
        if (!taskList) {
          console.log("📡 Fetching task list from API...");
          await api.getTaskList(listId);
        }

        console.log("📡 Fetching tasks for listId:", listId);
        try {
          await api.fetchTasks(listId);
        } catch (error) {
          console.warn("⚠️ Tasks not available yet for listId:", listId);
        }
      } catch (error) {
        console.error("❌ Error loading task list:", error);
      } finally {
        setIsLoading(false);
      }
    };

    loadInitialData();
  }, [listId]);

  console.log("📌 Current state.tasks:", state.tasks);

  const completionPercentage = React.useMemo(() => {
    if (listId && state.tasks[listId]) {
      const tasks = state.tasks[listId];
      const closedTasks = tasks.filter(
        (task) => task.status === TaskStatus.CLOSED
      ).length;
      return tasks.length > 0 ? (closedTasks / tasks.length) * 100 : 0;
    }
    return 0;
  }, [state.tasks, listId]);

  console.log("📊 Task completion percentage:", completionPercentage);

  const toggleStatus = (task: Task) => {
    if (listId) {
      console.log("🔄 Toggling status for task:", task);
      const updatedTask = { ...task, status: task.status === TaskStatus.CLOSED ? TaskStatus.OPEN : TaskStatus.CLOSED };
      api.updateTask(listId, task.id!, updatedTask).then(() => api.fetchTasks(listId));
    }
  };

  const deleteTaskList = async () => {
    if (listId) {
      console.log("🗑️ Deleting task list with listId:", listId);
      await api.deleteTaskList(listId);
      navigate("/");
    }
  };

  const handleAddTask = () => {
    if (listId) {
      navigate(`/task-lists/${listId}/new-task`);
    }
  };

  const tableRows = () => {
    if (!listId || !state.tasks[listId]) {
      console.warn("⚠️ No tasks found for listId:", listId);
      return <TableRow><TableCell colSpan={5}>No tasks available</TableCell></TableRow>;
    }

    console.log("📌 Rendering tasks:", state.tasks[listId]);

    return state.tasks[listId].map((task) => (
      <TableRow key={task.id} className="border-t">
        <TableCell className="px-4 py-2">
          <Checkbox
            isSelected={TaskStatus.CLOSED == task.status}
            onValueChange={() => toggleStatus(task)}
            aria-label={`Mark task "${task.title}" as ${TaskStatus.CLOSED == task.status ? "open" : "closed"}`}
          />
        </TableCell>
        <TableCell className="px-4 py-2">{task.title}</TableCell>
        <TableCell className="px-4 py-2">{task.priority}</TableCell>
        <TableCell className="px-4 py-2">
          {task.dueDate && (
            <DateInput
              isDisabled
              defaultValue={parseDate(new Date(task.dueDate).toISOString().split("T")[0])}
              aria-label={`Due date for task "${task.title}"`}
            />
          )}
        </TableCell>
        <TableCell className="px-4 py-2">
          <Button variant="ghost" onClick={() => listId && task.id && api.deleteTask(listId, task.id)} aria-label={`Delete task "${task.title}"`}>
            <Trash className="h-4 w-4" />
          </Button>
        </TableCell>
      </TableRow>
    ));
  };

  if (isLoading) {
    console.log("⏳ Loading data...");
    return <Spinner />;
  }

  return (
    <div className="p-4 max-w-4xl mx-auto">
      <div className="flex items-center gap-4 mb-4">
        <Button
          variant="light"
          isIconOnly
          onClick={() => navigate("/")}
          aria-label="Back to task lists"
        >
          <ArrowLeft size={20} />
        </Button>
        <h1 className="text-2xl font-bold">{taskList ? taskList.title : "Unknown Task List"}</h1>
      </div>
      <Progress value={completionPercentage} className="mb-4" />
      <Button onClick={handleAddTask} className="mb-4 w-full">
        <Plus className="h-4 w-4" /> Add Task
      </Button>
      <Table className="w-full">
        <TableHeader>
          <TableColumn>Completed</TableColumn>
          <TableColumn>Title</TableColumn>
          <TableColumn>Priority</TableColumn>
          <TableColumn>Due Date</TableColumn>
          <TableColumn>Actions</TableColumn>
        </TableHeader>
        <TableBody>{tableRows()}</TableBody>
      </Table>
      <Button color="danger" startContent={<Minus size={20} />} onClick={deleteTaskList}>
        Delete TaskList
      </Button>
    </div>
  );
};

export default TaskListScreen;

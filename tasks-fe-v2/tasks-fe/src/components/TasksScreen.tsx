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
import { useNavigate, useParams } from "react-router-dom";
import { useAppContext } from "../AppProvider";
import Task from "../domain/Task";
import { TaskStatus } from "../domain/TaskStatus";

const TaskListScreen: React.FC = () => {
  const { state, api } = useAppContext();
  const { listId } = useParams();
  const [isLoading, setIsLoading] = useState(true);
  const navigate = useNavigate();

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
      api.updateTask(listId, task.id, updatedTask).then(() => api.fetchTasks(listId));
    }
  };

  const deleteTaskList = async () => {
    if (listId) {
      console.log("🗑️ Deleting task list with listId:", listId);
      await api.deleteTaskList(listId);
      navigate("/");
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
          <Button variant="ghost" aria-label={`Edit task "${task.title}"`} onClick={() => navigate(`/task-lists/${listId}/edit-task/${task.id}`)}>
            <Edit className="h-4 w-4" />
          </Button>
          <Button variant="ghost" onClick={() => api.deleteTask(listId, task.id)} aria-label={`Delete task "${task.title}"`}>
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
      <h1 className="text-2xl font-bold">{taskList ? taskList.title : "Unknown Task List"}</h1>
      <Progress value={completionPercentage} className="mb-4" />
      <Button onClick={() => navigate(`/task-lists/${listId}/new-task`)} className="mb-4 w-full">
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























// import { parseDate } from "@internationalized/date";
// import {
//   Button,
//   Checkbox,
//   DateInput,
//   Progress,
//   Spacer,
//   Table,
//   TableBody,
//   TableCell,
//   TableColumn,
//   TableHeader,
//   TableRow,
//   Spinner,
// } from "@nextui-org/react";
// import { ArrowLeft, Edit, Minus, Plus, Trash } from "lucide-react";
// import React, { useEffect, useState } from "react";
// import { useNavigate, useParams } from "react-router-dom";
// import { useAppContext } from "../AppProvider";
// import Task from "../domain/Task";
// import { TaskStatus } from "../domain/TaskStatus";





// const TaskListScreen: React.FC = () => {

//   const { state, api } = useAppContext();
//   const { listId } = useParams();
//   const [isLoading, setIsLoading] = useState(true);
//   const navigate = useNavigate();

  
//   // Find task list directly from state instead of maintaining separate state
//   const taskList = state.taskLists.find((tl) => listId === tl.id);

//   // Single useEffect to handle all initial data loading
//   useEffect(() => {
//     const loadInitialData = async () => {
//       if (!listId) return;

//       setIsLoading(true);
//       try {
//         // Only fetch if we don't already have the task list
//         if (!taskList) {
//           await api.getTaskList(listId);
//         }

//         // Attempt to fetch tasks - this may 404 but we'll try anyway
//         try {
//           await api.fetchTasks(listId);
//         } catch (error) {
//           console.log("Tasks not available yet");
//         }
//       } catch (error) {
//         console.error("Error loading task list:", error);
//       } finally {
//         setIsLoading(false);
//       }
//     };

//     loadInitialData();
//   }, [listId]); // Only depend on listId

//   // Calculate completion percentage based on tasks
//   const completionPercentage = React.useMemo(() => {
//     if (listId && state.tasks[listId]) {
//       const tasks = state.tasks[listId];
//       const closeTaskCount = tasks.filter(
//         (task) => task.status === TaskStatus.CLOSED
//       ).length;
//       return tasks.length > 0 ? (closeTaskCount / tasks.length) * 100 : 0;
//     }
//     return 0;
//   }, [state.tasks, listId]);

//   const toggleStatus = (task: Task) => {
//     if (listId) {
//       const updatedTask = { ...task };
//       updatedTask.status =
//         task.status === TaskStatus.CLOSED ? TaskStatus.OPEN : TaskStatus.CLOSED;

//       api
//         .updateTask(listId, task.id, updatedTask)
//         .then(() => api.fetchTasks(listId));
//     }
//   };

//   const deleteTaskList = async () => {
//     if (null != listId) {
//       await api.deleteTaskList(listId);
//       navigate("/");
//     }
//   };

//   const tableRows = () => {
//     if (null != listId && null != state.tasks[listId]) {
//       return state.tasks[listId].map((task) => (
//         <TableRow key={task.id} className="border-t">
//           <TableCell className="px-4 py-2">
//             <Checkbox
//               isSelected={TaskStatus.CLOSED == task.status}
//               onValueChange={() => toggleStatus(task)}
//               aria-label={`Mark task "${task.title}" as ${
//                 TaskStatus.CLOSED == task.status ? "open" : "closed"
//               }`}
//             />
//           </TableCell>
//           <TableCell className="px-4 py-2">{task.title}</TableCell>
//           <TableCell className="px-4 py-2">{task.priority}</TableCell>
//           <TableCell className="px-4 py-2">
//             {task.dueDate && (
//               <DateInput
//                 isDisabled
//                 defaultValue={parseDate(
//                   new Date(task.dueDate).toISOString().split("T")[0]
//                 )}
//                 aria-label={`Due date for task "${task.title}"`}
//               />
//             )}
//           </TableCell>
//           <TableCell className="px-4 py-2">
//             <div className="flex space-x-2">
//               <Button
//                 variant="ghost"
//                 aria-label={`Edit task "${task.title}"`}
//                 onClick={() =>
//                   navigate(`/task-lists/${listId}/edit-task/${task.id}`)
//                 }
//               >
//                 <Edit className="h-4 w-4" />
//               </Button>
//               <Button
//                 variant="ghost"
//                 onClick={() => api.deleteTask(listId, task.id)}
//                 aria-label={`Delete task "${task.title}"`}
//               >
//                 <Trash className="h-4 w-4" />
//               </Button>
//             </div>
//           </TableCell>
//         </TableRow>
//       ));
//     } else {
//       return null;
//     }
//   };

//   if (isLoading) {
//     return <Spinner />; // Or your preferred loading indicator
//   }

//   return (
//     <div className="p-4 max-w-4xl mx-auto">
//       <div className="flex items-center justify-between mb-6">
//         <div className="flex w-full items-center justify-between">
//           <Button
//             variant="ghost"
//             aria-label="Go back to Task Lists"
//             onClick={() => navigate("/")}
//           >
//             <ArrowLeft className="h-4 w-4" />
//           </Button>

//           <h1 className="text-2xl font-bold mx-4">
//             {taskList ? taskList.title : "Unknown Task List"}
//           </h1>

//           <Button
//             variant="ghost"
//             aria-label={`Edit task list`}
//             onClick={() => navigate(`/edit-task-list/${listId}`)}
//           >
//             <Edit className="h-4 w-4" />
//           </Button>
//         </div>
//       </div>

//       <Progress
//         value={completionPercentage}
//         className="mb-4"
//         aria-label="Task completion progress"
//       />
//       <Button
//         onClick={() => navigate(`/task-lists/${listId}/new-task`)}
//         aria-label="Add new task"
//         className="mb-4 w-full"
//       >
//         <Plus className="h-4 w-4" /> Add Task
//       </Button>
//       <div className="border rounded-lg overflow-hidden">
//         <Table className="w-full" aria-label="Tasks list">
//           <TableHeader>
//             <TableColumn>Completed</TableColumn>
//             <TableColumn>Title</TableColumn>
//             <TableColumn>Priority</TableColumn>
//             <TableColumn>Due Date</TableColumn>
//             <TableColumn>Actions</TableColumn>
//           </TableHeader>
//           <TableBody>{tableRows()}</TableBody>
//         </Table>
//       </div>
//       <Spacer y={4} />
//       <div className="flex justify-end">
//         <Button
//           color="danger"
//           startContent={<Minus size={20} />}
//           onClick={deleteTaskList}
//           aria-label="Delete current task list"
//         >
//           Delete TaskList
//         </Button>
//       </div>

//       <Spacer y={4} />
//     </div>
//   );
// };

// export default TaskListScreen;

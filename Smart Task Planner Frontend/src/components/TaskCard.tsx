import { useState } from 'react';
import { CheckCircle2, Circle, Calendar, Link } from 'lucide-react';

interface Task {
  taskDescription: string;
  deadline: string;
  // This is the main fix: changed from string[] to string | null
  dependencies: string | null;
}

interface TaskCardProps {
  task: Task;
}

function TaskCard({ task }: TaskCardProps) {
  const [isCompleted, setIsCompleted] = useState(false);

  return (
    <div
      className={`rounded-lg shadow-sm p-5 transition-all duration-300 ${
        isCompleted ? 'bg-[#e8f5e9]' : 'bg-white'
      }`}
    >
      <div className="flex items-start gap-4">
        <button
          onClick={() => setIsCompleted(!isCompleted)}
          className="mt-0.5 flex-shrink-0 focus:outline-none focus:ring-2 focus:ring-gray-400 rounded-full"
          aria-label={isCompleted ? 'Mark as incomplete' : 'Mark as complete'}
        >
          {isCompleted ? (
            <CheckCircle2 className="w-5 h-5 text-green-600" />
          ) : (
            <Circle className="w-5 h-5 text-gray-400 hover:text-gray-600 transition-colors" />
          )}
        </button>

        <div className="flex-1 min-w-0">
          <p
            className={`text-base font-medium mb-2 ${
              isCompleted ? 'line-through text-gray-600' : 'text-gray-900'
            }`}
          >
            {task.taskDescription}
          </p>

          <div className="space-y-1.5">
            {task.deadline && (
              <div className="flex items-center gap-2 text-sm text-gray-500">
                <Calendar className="w-3.5 h-3.5 flex-shrink-0" />
                <span>Deadline: {task.deadline}</span>
              </div>
            )}

            {/* This block is updated to handle a string, not an array */}
            {task.dependencies && (
              <div className="flex items-start gap-2 text-sm text-gray-500">
                <Link className="w-3.5 h-3.5 mt-0.5 flex-shrink-0" />
                <div>
                  <span className="font-medium">Dependencies: </span>
                  {/* It now directly renders the string */}
                  <span>{task.dependencies}</span>
                </div>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

export default TaskCard;
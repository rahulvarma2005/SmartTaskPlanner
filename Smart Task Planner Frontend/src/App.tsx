import { useState } from 'react';
import { Loader2, Target } from 'lucide-react';
import TaskCard from './components/TaskCard';

interface Task {
  taskDescription: string;
  deadline: string;
  dependencies: string | null;
}

function App() {
  const [goal, setGoal] = useState('');
  const [tasks, setTasks] = useState<Task[]>([]);
  const [isLoading, setIsLoading] = useState(false);

  const handleGenerateTasks = async () => {
    if (!goal.trim()) return;

    setIsLoading(true);

    try {
      const response = await fetch('http://localhost:8081/api/tasks/generate', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ goal: goal.trim() }),
      });

      if (!response.ok) {
        throw new Error('Failed to generate tasks');
      }

      const data = await response.json();
      setTasks(data);
    } catch (error) {
      console.error('Error generating tasks:', error);
    } finally {
      setIsLoading(false);
    }
  };

  const handleKeyPress = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') {
      handleGenerateTasks();
    }
  };

  return (
    <div className="min-h-screen bg-[#f7f9fc] flex items-center justify-center px-4 py-12">
      <div className="w-full max-w-2xl">
        <div className="text-center mb-10">
          <div className="flex items-center justify-center gap-3 mb-3">
            <Target className="w-8 h-8 text-gray-800" />
            <h1 className="text-4xl font-semibold text-gray-900">Smart Task Planner</h1>
          </div>
          <p className="text-base text-gray-600">
            Enter your goal and let AI create a detailed action plan for you
          </p>
        </div>

        <div className="mb-8">
          <input
            id="goal-input"
            type="text"
            value={goal}
            onChange={(e) => setGoal(e.target.value)}
            onKeyPress={handleKeyPress}
            disabled={isLoading}
            placeholder="e.g., Launch a new mobile app, Plan a wedding, Learn web development..."
            className="w-full px-5 py-3.5 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-gray-400 focus:border-transparent disabled:bg-gray-100 disabled:text-gray-500 disabled:cursor-not-allowed text-base transition-all shadow-sm"
          />

          {isLoading && (
            <div className="mt-6 flex items-center justify-center gap-3 text-gray-600">
              <Loader2 className="w-5 h-5 animate-spin" />
              <span className="text-sm">Generating your plan...</span>
            </div>
          )}
        </div>

        {!isLoading && tasks.length > 0 && (
          <div>
            <h2 className="text-xl font-medium text-gray-900 mb-5">
              Your Action Plan ({tasks.length} tasks)
            </h2>
            <div className="space-y-3">
              {tasks.map((task, index) => (
                <TaskCard key={index} task={task} />
              ))}
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

export default App;

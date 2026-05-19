import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../auth/context/AuthContext';
import '../../styles/dashboard.css';

function DashboardPage() {
  const navigate = useNavigate();
  const { user, logout } = useAuth();

  const handleLogout = () => {
    logout();
    navigate('/login', { replace: true });
  };

  // Placeholders: cada módulo será habilitado en sprints posteriores
  const modules = [
    { icon: '♔', title: 'Jugar vs CPU', desc: 'Stockfish 18 en la nube', sprint: 'Sprint 2' },
    { icon: '⚔️', title: 'Multijugador Local', desc: 'Dos jugadores, un PC', sprint: 'Sprint 2' },
    { icon: '🌐', title: 'Multijugador Remoto', desc: 'Partidas en línea', sprint: 'Sprint 3' },
    { icon: '🧠', title: 'Tutoría IA', desc: 'Análisis post-partida con DeepSeek', sprint: 'Sprint 4' },
    { icon: '📊', title: 'Historial Lichess', desc: 'Importa tus partidas', sprint: 'Sprint 3' },
    { icon: '🔮', title: 'Predicciones IA', desc: 'Resultados probabilísticos', sprint: 'Sprint 5' },
  ];

  return (
    <div className="dashboard">
      <header className="dashboard__header">
        <div className="dashboard__brand">
          <span className="dashboard__brand-icon">♞</span>
          <span>Ajedrez App</span>
        </div>
        <div className="dashboard__user">
          <span className="dashboard__user-name">{user?.username}</span>
          <span className="dashboard__user-role">{user?.role}</span>
          <button className="dashboard__logout" onClick={handleLogout}>
            Cerrar sesión
          </button>
        </div>
      </header>

      <main className="dashboard__main">
        <section className="dashboard__welcome">
          <h1>Bienvenido, {user?.username} 👋</h1>
          <p>Tu plataforma central de análisis y juego de ajedrez.</p>
        </section>

        <section className="dashboard__grid" aria-label="Módulos disponibles">
          {modules.map((m) => (
            <article key={m.title} className="dashboard__module">
              <div className="dashboard__module-icon">{m.icon}</div>
              <h2>{m.title}</h2>
              <p>{m.desc}</p>
              <span className="dashboard__module-tag">Disponible en {m.sprint}</span>
            </article>
          ))}
        </section>
      </main>
    </div>
  );
}

export default DashboardPage;

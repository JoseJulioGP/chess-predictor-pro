import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import '../../styles/auth-forms.css';

function LoginForm() {
  const navigate = useNavigate();
  const { login } = useAuth();
  const [formData, setFormData] = useState({ email: '', password: '' });
  const [error, setError] = useState(null);
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleChange = (e) => {
    setFormData((prev) => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    setIsSubmitting(true);

    try {
      await login(formData);
      navigate('/dashboard', { replace: true });
    } catch (err) {
      const apiMessage = err.response?.data?.message;
      setError(apiMessage || 'Error al iniciar sesión. Verifica tus credenciales.');
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <form className="auth-form" onSubmit={handleSubmit} noValidate>
      <h1 className="auth-form__title">Iniciar Sesión</h1>
      <p className="auth-form__subtitle">Entra a tu cuenta de ajedrez</p>

      <div className="auth-form__field">
        <label htmlFor="email">Email</label>
        <input
          id="email"
          name="email"
          type="email"
          autoComplete="email"
          value={formData.email}
          onChange={handleChange}
          required
          placeholder="tu@email.com"
        />
      </div>

      <div className="auth-form__field">
        <label htmlFor="password">Contraseña</label>
        <input
          id="password"
          name="password"
          type="password"
          autoComplete="current-password"
          value={formData.password}
          onChange={handleChange}
          required
          placeholder="••••••••"
        />
      </div>

      {error && <div className="auth-form__error" role="alert">{error}</div>}

      <button
        type="submit"
        className="auth-form__submit"
        disabled={isSubmitting}
      >
        {isSubmitting ? 'Entrando...' : 'Entrar'}
      </button>

      <p className="auth-form__footer">
        ¿No tienes cuenta? <Link to="/register">Regístrate aquí</Link>
      </p>
    </form>
  );
}

export default LoginForm;

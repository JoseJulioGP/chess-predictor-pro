import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import '../../styles/auth-forms.css';

function RegisterForm() {
  const navigate = useNavigate();
  const { register } = useAuth();
  const [formData, setFormData] = useState({
    email: '',
    username: '',
    password: '',
    confirmPassword: '',
  });
  const [error, setError] = useState(null);
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleChange = (e) => {
    setFormData((prev) => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const validateLocally = () => {
    if (formData.password !== formData.confirmPassword) {
      return 'Las contraseñas no coinciden.';
    }
    if (formData.password.length < 8) {
      return 'La contraseña debe tener al menos 8 caracteres.';
    }
    return null;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const localError = validateLocally();
    if (localError) {
      setError(localError);
      return;
    }
    setError(null);
    setIsSubmitting(true);

    try {
      await register({
        email: formData.email,
        username: formData.username,
        password: formData.password,
      });
      navigate('/dashboard', { replace: true });
    } catch (err) {
      const apiMessage = err.response?.data?.message;
      const details = err.response?.data?.details;
      setError(
        details?.length
          ? details.join(', ')
          : apiMessage || 'No se pudo completar el registro.'
      );
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <form className="auth-form" onSubmit={handleSubmit} noValidate>
      <h1 className="auth-form__title">Crear Cuenta</h1>
      <p className="auth-form__subtitle">Únete a la plataforma</p>

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
        <label htmlFor="username">Nombre de jugador</label>
        <input
          id="username"
          name="username"
          type="text"
          autoComplete="username"
          value={formData.username}
          onChange={handleChange}
          required
          minLength={3}
          maxLength={30}
          placeholder="MagnusCarlsen"
        />
      </div>

      <div className="auth-form__field">
        <label htmlFor="password">Contraseña</label>
        <input
          id="password"
          name="password"
          type="password"
          autoComplete="new-password"
          value={formData.password}
          onChange={handleChange}
          required
          minLength={8}
          placeholder="Mínimo 8 caracteres"
        />
      </div>

      <div className="auth-form__field">
        <label htmlFor="confirmPassword">Confirmar contraseña</label>
        <input
          id="confirmPassword"
          name="confirmPassword"
          type="password"
          autoComplete="new-password"
          value={formData.confirmPassword}
          onChange={handleChange}
          required
          placeholder="Repite tu contraseña"
        />
      </div>

      {error && <div className="auth-form__error" role="alert">{error}</div>}

      <button type="submit" className="auth-form__submit" disabled={isSubmitting}>
        {isSubmitting ? 'Creando...' : 'Crear cuenta'}
      </button>

      <p className="auth-form__footer">
        ¿Ya tienes cuenta? <Link to="/login">Inicia sesión</Link>
      </p>
    </form>
  );
}

export default RegisterForm;

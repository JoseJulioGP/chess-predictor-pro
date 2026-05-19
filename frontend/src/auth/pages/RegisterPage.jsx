import RegisterForm from '../components/RegisterForm';
import '../../styles/auth-pages.css';

function RegisterPage() {
  return (
    <div className="auth-page">
      <div className="auth-page__board" aria-hidden="true" />
      <div className="auth-page__card">
        <div className="auth-page__brand">
          <span className="auth-page__brand-icon">♛</span>
          <span className="auth-page__brand-text">Ajedrez App</span>
        </div>
        <RegisterForm />
      </div>
    </div>
  );
}

export default RegisterPage;

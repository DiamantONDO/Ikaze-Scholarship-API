<template>
  <div class="home">

    <nav class="navbar">
      <div class="nav-logo">
        <RouterLink to="/home" class="logo-link">
        <span class="logo-icon"></span>
        <h2 class="logo">IKAZEScholarship</h2>
      </RouterLink>
      </div>
      <div class="nav-links">
        <RouterLink to="/login" class="nav-link">Sign In</RouterLink>
        <RouterLink to="/register" class="nav-btn">Get Started</RouterLink>
      </div>
    </nav>

    <section class="hero">
      <div class="hero-content">
        <span class="hero-badge">Rwanda's #1 Scholarship Platform</span>
        <h1>Find and Apply for <span class="highlight">Scholarships</span> Easily</h1>
        <p>Connecting students with sponsors and administrators to unlock educational opportunities across Rwanda.</p>
        <div class="hero-buttons">
          <RouterLink to="/register/student" class="btn primary">Apply as Student →</RouterLink>
          <RouterLink to="/register" class="btn secondary">Register as Sponsor</RouterLink>
        </div>
      </div>
    </section>

    <section class="stats-section">
      <div class="stat">
        <h3>500+</h3>
        <p>Students Supported</p>
      </div>
      <div class="stat">
        <h3>50+</h3>
        <p>Active Scholarships</p>
      </div>
      <div class="stat">
        <h3>30+</h3>
        <p>Sponsors and Partners</p>
      </div>
      <div class="stat">
        <h3>100%</h3>
        <p>Free to Apply</p>
      </div>
    </section>

    <section class="how-it-works">
      <h2>How It Works</h2>
      <p class="section-sub">Three simple steps to get your scholarship</p>

      <div class="steps">
        <div class="step">
          <div class="step-icon"></div>
          <h3>1. Register</h3>
          <p>Create your student account with your personal and academic information.</p>
        </div>
        <div class="step-arrow">→</div>
        <div class="step">
          <div class="step-icon"></div>
          <h3>2. Browse</h3>
          <p>Explore available scholarships and find the ones that match your field.</p>
        </div>
        <div class="step-arrow">→</div>
        <div class="step">
          <div class="step-icon"></div>
          <h3>3. Apply</h3>
          <p>Submit your application with required documents and await approval.</p>
        </div>
      </div>
    </section>

    <section class="roles-section">
      <h2>Who Is This For?</h2>

      <div class="roles">
        <div class="role-card">
          <div class="role-icon"></div>
          <h3>Students</h3>
          <p>Browse and apply for scholarships, track your applications, and receive payments directly.</p>
          <RouterLink to="/register/student" class="role-link">Register as Student →</RouterLink>
        </div>

        <div class="role-card featured">
          <div class="role-icon"></div>
          <h3>Sponsors</h3>
          <p>Post scholarships, review student applications, and manage payments all in one place.</p>
          <RouterLink to="/register" class="role-link">Register as Sponsor →</RouterLink>
        </div>

        <div class="role-card">
          <div class="role-icon"></div>
          <h3>Admins</h3>
          <p>Oversee the entire platform, manage users, approve applications, and monitor payments.</p>
          <RouterLink to="/login" class="role-link">Admin Login →</RouterLink>
        </div>
      </div>
    </section>

    <footer class="footer">
      <div class="footer-content">
        <div class="footer-logo">
          <span></span>
          <h3>IKAZEScholarship</h3>
        </div>
        <p>Empowering students through accessible education funding.</p>
      </div>
      <p class="footer-copy">© 2026 IKAZEScholarship. All rights reserved.</p>
    </footer>

  </div>
</template>

<style scoped>
* { box-sizing: border-box; margin: 0; padding: 0; }

.home {
  font-family: Arial, Helvetica, sans-serif;
  color: #1e3a8a;
}

.logo{
  font-size: 48px;
  margin-bottom: 12px;
  font-weight: bolder;
}

.logo-link {
  text-decoration: none;
  color: #1e3a8a;
  padding-top: 0;
  padding-bottom: 0;
}

.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 18px 60px;
  background: white;
  box-shadow: 0 2px 12px rgba(0,0,0,0.08);
  position: sticky;
  top: 0;
  z-index: 100;
}

.nav-logo {
  display: flex;
  align-items: center;
  gap: 10px;
}

.logo-icon { 
  font-size: 28px;
 }

.nav-logo h2 {
  font-size: 22px;
  color: #1e3a8a;
}

.nav-links {
  display: flex;
  align-items: center;
  gap: 20px;
}

.nav-link {
  text-decoration: none;
  color: #1e3a8a;
  font-weight: bold;
  font-size: 15px;
}

.nav-btn {
  background: #1e3a8a;
  color: white;
  padding: 10px 22px;
  border-radius: 8px;
  text-decoration: none;
  font-weight: bold;
  font-size: 14px;
  transition: background 0.2s;
}

.nav-btn:hover { background: #2d4fa3; }

.hero {
  min-height: 88vh;
  background-image: url('public/Riviera.jpg');
  background-size: cover;
  background-position: center;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  padding: 60px 20px;
  position: relative;
}

.hero::before {
  content: '';
  position: absolute;
  inset: 0;
  background: rgba(10, 25, 70, 0.6);
}

.hero-content {
  position: relative;
  z-index: 1;
  max-width: 700px;
  color: white;
}

.hero-badge {
  display: inline-block;
  background: rgba(255,255,255,0.15);
  border: 1px solid rgba(255,255,255,0.3);
  padding: 6px 18px;
  border-radius: 20px;
  font-size: 13px;
  margin-bottom: 20px;
}

.hero-content h1 {
  font-size: 48px;
  line-height: 1.2;
  margin-bottom: 20px;
  font-weight: bold;
}

.highlight { color: #60a5fa; }

.hero-content p {
  font-size: 18px;
  opacity: 0.9;
  margin-bottom: 36px;
  line-height: 1.6;
}

.hero-buttons {
  display: flex;
  gap: 16px;
  justify-content: center;
  flex-wrap: wrap;
}

.btn {
  padding: 14px 28px;
  border-radius: 10px;
  text-decoration: none;
  font-weight: bold;
  font-size: 15px;
  transition: all 0.2s;
}

.btn.primary {
  background: #3b82f6;
  color: white;
}

.btn.primary:hover { background: #2563eb; }

.btn.secondary {
  background: rgba(255,255,255,0.15);
  color: white;
  border: 2px solid rgba(255,255,255,0.5);
}

.btn.secondary:hover { background: rgba(255,255,255,0.25); }

.stats-section {
  display: flex;
  justify-content: center;
  gap: 0;
  background: #1e3a8a;
  padding: 40px 60px;
}

.stat {
  flex: 1;
  text-align: center;
  color: white;
  padding: 20px;
  border-right: 1px solid rgba(255,255,255,0.15);
}

.stat:last-child { border-right: none; }

.stat h3 {
  font-size: 36px;
  font-weight: bold;
  color: #60a5fa;
}

.stat p {
  font-size: 14px;
  opacity: 0.85;
  margin-top: 6px;
}

.how-it-works {
  padding: 80px 60px;
  text-align: center;
  background: #f9fafb;
  background-image: url("public/image4.jpg");
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
}

.how-it-works h2 {
  font-size: 32px;
  margin-bottom: 10px;
  color: #ffffff;
}

.section-sub {
  color: #ffffff;
  font-size: 16px;
  margin-bottom: 50px;
}

.steps {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 20px;
  flex-wrap: wrap;
}

.step {
  background: white;
  border-radius: 16px;
  padding: 36px 28px;
  width: 220px;
  box-shadow: 0 4px 16px rgba(0,0,0,0.07);
}

.step-icon { font-size: 40px; margin-bottom: 16px; }

.step h3 {
  font-size: 16px;
  margin-bottom: 10px;
  color: #1e3a8a;
}

.step p {
  font-size: 13px;
  color: #6b7280;
  line-height: 1.5;
}

.step-arrow {
  font-size: 28px;
  color: #3b82f6;
  font-weight: bold;
}

.roles-section {
  padding: 80px 60px;
  text-align: center;
  background: white;
}

.roles-section h2 {
  font-size: 32px;
  margin-bottom: 10px;
}

.roles {
  display: flex;
  gap: 24px;
  justify-content: center;
  margin-top: 40px;
  flex-wrap: wrap;
}

.role-card {
  flex: 1;
  min-width: 240px;
  max-width: 300px;
  background: #f9fafb;
  border-radius: 16px;
  padding: 36px 24px;
  border: 2px solid #e5e7eb;
  transition: all 0.2s;
}

.role-card:hover {
  border-color: #3b82f6;
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(0,0,0,0.1);
}

.role-card.featured {
  background: #1e3a8a;
  color: white;
  border-color: #1e3a8a;
}

.role-card.featured h3,
.role-card.featured p { color: white; }

.role-icon { font-size: 40px; margin-bottom: 16px; }

.role-card h3 {
  font-size: 18px;
  margin-bottom: 10px;
  color: #1e3a8a;
}

.role-card p {
  font-size: 14px;
  color: #6b7280;
  line-height: 1.6;
  margin-bottom: 20px;
}

.role-link {
  font-size: 14px;
  font-weight: bold;
  color: #3b82f6;
  text-decoration: none;
}

.role-card.featured .role-link { color: #93c5fd; }

.role-link:hover { text-decoration: underline; }

.footer {
  background: #111827;
  color: white;
  padding: 40px 60px 24px;
  text-align: center;
}

.footer-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
  margin-bottom: 20px;
}

.footer-logo {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 24px;
}

.footer-logo h3 { font-size: 20px; }

.footer-content p {
  color: #9ca3af;
  font-size: 14px;
}

.footer-copy {
  color: #6b7280;
  font-size: 12px;
  border-top: 1px solid #1f2937;
  padding-top: 20px;
}
</style>
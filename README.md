This repository contains a Spring Boot-based microservices architecture for managing loans, mandates, KYC, bank accounts, and notifications.

---

<h3>🧩 Microservices</h3>
<table>
  <thead>
    <tr>
      <th>Microservice</th>
      <th>Description</th>
    </tr>
  </thead>
  <tbody>
    <tr><td><strong>Eureka Server</strong></td><td>Service discovery</td></tr>
    <tr><td><strong>API Gateway</strong></td><td>Central API access, routing, CORS, security</td></tr>
    <tr><td><strong>User Service</strong></td><td>Google OAuth2 login, user-role management</td></tr>
    <tr><td><strong>Bank Account Service</strong></td><td>Bank account linking and approval</td></tr>
    <tr><td><strong>KYC Service</strong></td><td>Upload, verify, and approve Aadhaar/PAN</td></tr>
    <tr><td><strong>Loan Service</strong></td><td>Apply for and manage loans</td></tr>
    <tr><td><strong>Mandate Service</strong></td><td>Create and manage eMandates (auto-debit)</td></tr>
    <tr><td><strong>Notification Service</strong></td><td>Kafka consumer for loan, mandate, and KYC events. Sends notifications via WebSocket (real-time updates) and SMS (template-based, pluggable)</td></tr>
    <tr><td><strong>Scheduler Service</strong></td><td>Scheduled tasks via Kafka (e.g., EMI deduction)</td></tr>
   
  </tbody>
</table>

---

<h3>⚙️ Tech Stack</h3>
<table>
  <thead>
    <tr>
      <th>Category</th>
      <th>Technologies Used</th>
    </tr>
  </thead>
  <tbody>
    <tr><td><strong>Backend</strong></td><td>Spring Boot + Spring Cloud (Eureka, Gateway)</td></tr>
    <tr><td><strong>Messaging</strong></td><td>Kafka – Event-driven architecture</td></tr>
    <tr><td><strong>Databases</strong></td><td>PostgreSQL(neondb) – Hybrid DBs across services</td></tr>
    <tr><td><strong>Auth</strong></td><td>OAuth2 (Google)</td></tr>
    <tr><td><strong>Real-Time</strong></td><td>WebSocket (STOMP) + SockJS – Real-time client updates</td></tr>
    <tr><td><strong>Scheduling</strong></td><td>Quartz Scheduler – Scheduled jobs/events</td></tr>
    <tr><td><strong>SMS Integration</strong></td><td>Twilio</td></tr>
  </tbody>
</table>

---


<h3>🧪 Running Locally</h3>
<table>
  <thead>
    <tr>
      <th>Step</th>
      <th>Action</th>
    </tr>
  </thead>
  <tbody>
    <tr><td>1</td><td>Start Eureka Server at <code>localhost:8761</code></td></tr>
    <tr><td>2</td><td>Start Kafka</td></tr>
    <tr><td>3</td><td>Start all services</td></tr>
    <tr><td>4</td><td>Access system via API Gateway at <code>http://localhost:8080</code></td></tr>
  </tbody>
</table>

<img src="https://autonance.s3.ap-south-1.amazonaws.com/eureka-server.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=ASIAYWBJYCZP6YFV2MQD%2F20250507%2Fap-south-1%2Fs3%2Faws4_request&X-Amz-Date=20250507T191253Z&X-Amz-Expires=300&X-Amz-Security-Token=IQoJb3JpZ2luX2VjELv%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FwEaCmFwLXNvdXRoLTEiSDBGAiEAurdNz8j3kAeNwbBpqWDLNvbs%2Bfc%2BNXXJg5kXFbFLVycCIQDvbzvA1E1oN2tnfz7kfub%2FLyzTECYjDmNNwHejgYumGCraAghkEAAaDDU5NzA4ODAxNTk2NyIMpdTJBgSVbYf9MVfOKrcCCJsrXr35icW6ZZGnCpWQwzxNwOWdvHzTyX641MsWDtgN%2BmJ0DaRX6JsU47IH7eJ5RUDBe3oC8k416kRcoX4yGAP8JTiroxlL7EICJbj%2FuupmcwTLh1%2B6YQlQ2TPoFzFB6MG2VRcQgPw9ikqbzGN0O7BA8wLs7TsC5WplKHAM6WsMJSdTJ0%2B6KMq47VPvBc2VJcSN7aPnPVfKc7oOzCmm9Ob4K954pbuAxov0Ef1ZuI8YVd0wXRNdqXfqMXW2gvdOQ73EYZ00i6w%2BCOUTKf5ADfLM1pvD%2B4zdxIiI0ORu5oog%2FsguSyLzF4Do0FWC2o3T0g7XaVErx0TjWrlngdQqDlkJWrzfk8ZWrOY5V1wd7TU%2FmURv%2FQrFg5wdPFTUFM4OavfeziqBBBMog6ylV83bboWTD9i1AxMw5eDuwAY6rAIG8VRX20xDOAAC7W%2FczuHPE7qjQiPk722vhroCm%2BaIN47Mz31H1CedI89ViMteQseudZx%2BrRQ6cSGU9FLQHg9KLkfDNwLmdDiW2Wcq6RZx1Ht2k10VxlpH6Y4UiJdd%2BOhqHNrn5nXJprYkl5DsuW8tmXNd8F5gaUZSv3VHiP6xSTXcirWSDLiqDU1c4e1r45DBhfs2axw9q%2FnoscUEHH9Quu4mvMejHAzO7R1Zsh4PpnA%2FmROzKEuj%2B9KO6q3c4FDwrXA3qdSSk2wCdHdLVeRXIE7esAcf3Oi7CY2UQocmQMNwPRAHlKmWyR7sptn3hmY4cMvTQ7otcTowQ0JfJybPEPYPr5RVxgQxDJVwENYEavVCTzgokNsWm3CoZV3jhcQ8n1pF4u23eLyy82w%3D&X-Amz-Signature=cee8f68126575d234216761d00ba1f7fd1f2b109a0b9b48ccc478d067eefdc6d&X-Amz-SignedHeaders=host&response-content-disposition=inline"/>





---


<h3>📦 Repository Structure</h3>
<pre>
microservices-springboot/
├── api-gateway/
├── eureka-server/
├── user-service/
├── bank-account-service/
├── kyc-service/
├── loan-service/
├── mandate-service/
├── notification-service/ # Kafka consumer, WebSocket, SMS
├── scheduler-service/ # Periodic jobs
</pre>



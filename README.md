# \# IKAZE Scholarship — REST API

# 

# Spring Boot backend for the IKAZE Scholarship platform, a system that connects Rwandan students with sponsors offering scholarship funding. This repository holds the REST API and its MongoDB data layer.

# 

# \*\*Live API:\*\* https://ikaze-scholarship-api.onrender.com/swagger-ui/index.html

# \*\*Live frontend:\*\* https://ikaze-scholarship-web.vercel.app

# \*\*Frontend repository:\*\* \[Ikaze-Scholarship-Web](https://github.com/DiamantONDO/Ikaze-Scholarship-Web)

# 

# Built as a coursework project for Web Technologies at the Adventist University of Central Africa (AUCA).

# 

# \---

# 

# \## Tech stack

# 

# \- Spring Boot 3.5 on Java 17

# \- Spring Data MongoDB

# \- Spring Web (REST controllers)

# \- Bean Validation

# \- Lombok

# \- MongoDB Atlas

# \- Deployed to Render as a Docker container

# 

# \---

# 

# \## Architecture

# 

# The project follows a standard layered structure:

# 

# ```

# Controller  →  Service  →  Repository  →  MongoDB

# ```

# 

# ```

# src/main/java/com/example/scholarship/

# ├── config/        CORS configuration and the data seeder

# ├── controller/    REST endpoints

# ├── dto/           request and response objects

# ├── exception/     custom exceptions and the global handler

# ├── model/         MongoDB documents

# ├── repository/    Spring Data repositories

# └── service/       business logic

# ```

# 

# \### Data model

# 

# | Collection | Purpose |

# |---|---|

# | `users` | Students, sponsors and administrators, distinguished by a `role` field |

# | `scholarships` | Funding opportunities posted by sponsors |

# | `applications` | Student applications, including uploaded documents |

# | `payments` | Disbursements recorded against a student and scholarship |

# 

# \---

# 

# \## API endpoints

# 

# \### Authentication

# 

# | Method | Endpoint | Description |

# |---|---|---|

# | `POST` | `/api/auth/login` | Log in with email and password |

# | `POST` | `/api/auth/register` | Register a sponsor or admin account |

# | `POST` | `/api/auth/register/student` | Register a student with their full profile |

# 

# \### Scholarships

# 

# | Method | Endpoint | Description |

# |---|---|---|

# | `GET` | `/api/scholarships` | List all scholarships |

# | `GET` | `/api/scholarships/active` | List only active scholarships |

# | `GET` | `/api/scholarships/sponsor/{id}` | Scholarships posted by one sponsor |

# | `POST` | `/api/scholarships` | Create a scholarship |

# | `DELETE` | `/api/scholarships/{id}` | Delete a scholarship |

# 

# \### Applications

# 

# | Method | Endpoint | Description |

# |---|---|---|

# | `GET` | `/api/applications` | List all applications |

# | `GET` | `/api/applications/student/{id}` | Applications by one student |

# | `GET` | `/api/applications/sponsor/{id}` | Applications to one sponsor's scholarships |

# | `POST` | `/api/applications` | Submit an application |

# | `PUT` | `/api/applications/{id}/status` | Approve or reject an application |

# 

# \### Payments

# 

# | Method | Endpoint | Description |

# |---|---|---|

# | `GET` | `/api/payments` | List all payments |

# | `GET` | `/api/payments/sponsor/{id}` | Payments made by one sponsor |

# | `GET` | `/api/payments/student/{id}` | Payments received by one student |

# | `POST` | `/api/payments` | Record a payment |

# 

# \### Users

# 

# | Method | Endpoint | Description |

# |---|---|---|

# | `GET` | `/api/users/students` | List all students |

# | `GET` | `/api/users/sponsors` | List all sponsors |

# | `GET` | `/api/users/{id}` | Fetch a single user |

# 

# Interactive documentation is available at `/swagger-ui.html` when the service is running.

# 

# \---

# 

# \## Running locally

# 

# You will need Java 17 or later and either a local MongoDB instance or a MongoDB Atlas connection string.

# 

# ```bash

# git clone https://github.com/DiamantONDO/Ikaze-Scholarship-API.git

# cd Ikaze-Scholarship-API

# ./mvnw spring-boot:run

# ```

# 

# With no environment variables set, the application falls back to `mongodb://localhost:27017/ikazescholarship` on port `8081`.

# 

# On first startup the seeder populates the database with 23 demonstration users — one admin, twelve sponsors and ten students.

# 

# \---

# 

# \## Environment variables

# 

# All configuration is externalised, so no credentials live in the repository.

# 

# | Variable | Purpose | Default |

# |---|---|---|

# | `MONGODB\_URI` | MongoDB connection string | `mongodb://localhost:27017/ikazescholarship` |

# | `PORT` | Port the server listens on | `8081` |

# | `CORS\_ALLOWED\_ORIGINS` | Comma-separated list of permitted frontend origins | `http://localhost:5173` |

# 

# \---

# 

# \## Deployment

# 

# The service is deployed to Render from the included `Dockerfile`, which uses a two-stage build: Maven compiles the JAR, then a slim JRE image runs it.

# 

# ```bash

# docker build -t ikaze-api .

# docker run -p 8081:8081 -e MONGODB\_URI="your-connection-string" ikaze-api

# ```

# 

# > The Render free tier sleeps after 15 minutes of inactivity. The first request after an idle period may take up to a minute while the container restarts.

# 

# \---

# 

# \## Validation and error handling

# 

# \- Bean Validation on incoming DTOs

# \- Uploaded documents are checked for PDF extension and a 5 MB size limit

# \- Input is sanitised before persistence

# \- A `GlobalExceptionHandler` maps custom exceptions to consistent JSON error responses

# 

# \---

# 

# \## Known limitations

# 

# \- Passwords are stored and compared in plain text; BCrypt hashing and JWT authentication are the natural next steps

# \- Endpoints are currently unauthenticated — authorisation is enforced only on the client side

# \- Documents are stored as base64 strings inside MongoDB rather than in object storage

# 

# These are acknowledged scope limits for a coursework build, not oversights.

# 

# \---

# 

# \## Author

# 

# \*\*ONDO ABESSOLO Diamant Anthony\*\*

# Software Engineering, Adventist University of Central Africa (AUCA), Kigali, Rwanda

# 

# GitHub: \[@DiamantONDO](https://github.com/DiamantONDO)


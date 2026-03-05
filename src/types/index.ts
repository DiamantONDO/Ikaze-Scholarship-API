export interface Scholarship {
  id: number;
  sponsorId: number;
  title: string;
  field: string;
  amount: number;
  deadline: string;
  status: "active" | "inactive";
}

export interface Payment {
  id: number;
  sponsorId: number;
  studentId: number;
  scholarshipId: number;
  month: string;
  amount: number;
  status: "paid" | "pending";
  date: string;
}

export interface Application {
  id: number;
  studentId: number;
  scholarshipId: number;
  status: string;
  date: string;
}

export interface User {
  id: number;
  fullName: string;
  email: string;
  role: "student" | "sponsor" | "admin";
}
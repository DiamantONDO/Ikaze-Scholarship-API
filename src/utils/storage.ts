export const getUsers = () => JSON.parse(localStorage.getItem("users") || "[]");
export const getScholarships = () => JSON.parse(localStorage.getItem("scholarships") || "[]");
export const getApplications = () => JSON.parse(localStorage.getItem("applications") || "[]");
export const getPayments = () => JSON.parse(localStorage.getItem("payments") || "[]");

export const saveUsers = (data: any[]) => localStorage.setItem("users", JSON.stringify(data));
export const saveScholarships = (data: any[]) => localStorage.setItem("scholarships", JSON.stringify(data));
export const saveApplications = (data: any[]) => localStorage.setItem("applications", JSON.stringify(data));
export const savePayments = (data: any[]) => localStorage.setItem("payments", JSON.stringify(data));

//Option2 
/* 
But for Option 2 I'd need to define the interfaces 
in storage.ts itself or import them:

import type { Scholarship, Payment, Application, User } from "@/types";

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

// src/utils/storage.ts
export const getScholarships = (): Scholarship[] => 
  JSON.parse(localStorage.getItem("scholarships") || "[]");

export const getPayments = (): Payment[] => 
  JSON.parse(localStorage.getItem("payments") || "[]");

export const getApplications = (): Application[] => 
  JSON.parse(localStorage.getItem("applications") || "[]");

export const getUsers = (): User[] => 
  JSON.parse(localStorage.getItem("users") || "[]");
*/
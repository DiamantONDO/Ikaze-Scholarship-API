export const formatDate = (date: string) =>
  new Date(date).toLocaleDateString("en-GB", { day: "2-digit", month: "long", year: "numeric" });

export const formatAmount = (amount: number) =>
  amount.toLocaleString() + " RWF";
export interface IGrade {
  id?: number;
  grade?: number;
}

export class Grade implements IGrade {
  constructor(public id?: number, public grade?: number) {}
}

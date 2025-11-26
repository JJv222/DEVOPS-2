import { ActivatedRouteSnapshot, ResolveFn, RouterStateSnapshot } from "@angular/router";
import { inject } from "@angular/core";
import { Screening } from "../models/screening";
import { screeningService } from "../services/screeningService";

export const screeningResolver: ResolveFn<Screening[]> = (
    route: ActivatedRouteSnapshot,  
    state: RouterStateSnapshot,
) => {
    const screeningId = Number(route.paramMap.get('id'));
    const result = inject(screeningService).getScreeningById(screeningId);
    result.subscribe(data => console.log('Resolver screening data:', data));
     return result;
};

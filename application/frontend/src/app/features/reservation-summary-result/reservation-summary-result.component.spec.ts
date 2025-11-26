import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservationSummaryResultComponent } from './reservation-summary-result.component';

describe('ReservationSummaryResultComponent', () => {
  let component: ReservationSummaryResultComponent;
  let fixture: ComponentFixture<ReservationSummaryResultComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReservationSummaryResultComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReservationSummaryResultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

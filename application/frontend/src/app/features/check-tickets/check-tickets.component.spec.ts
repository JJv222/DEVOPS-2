import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketsValidatorPanelComponent } from './check-tickets.component';

describe('TicketsValidatorPanelComponent', () => {
  let component: TicketsValidatorPanelComponent;
  let fixture: ComponentFixture<TicketsValidatorPanelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketsValidatorPanelComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TicketsValidatorPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

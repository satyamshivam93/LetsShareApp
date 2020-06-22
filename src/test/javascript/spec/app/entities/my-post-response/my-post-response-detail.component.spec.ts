import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LetsShareAppTestModule } from '../../../test.module';
import { MyPostResponseDetailComponent } from 'app/entities/my-post-response/my-post-response-detail.component';
import { MyPostResponse } from 'app/shared/model/my-post-response.model';

describe('Component Tests', () => {
  describe('MyPostResponse Management Detail Component', () => {
    let comp: MyPostResponseDetailComponent;
    let fixture: ComponentFixture<MyPostResponseDetailComponent>;
    const route = ({ data: of({ myPostResponse: new MyPostResponse(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LetsShareAppTestModule],
        declarations: [MyPostResponseDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MyPostResponseDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MyPostResponseDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load myPostResponse on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.myPostResponse).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

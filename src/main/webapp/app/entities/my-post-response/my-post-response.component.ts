import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IMyPostResponse } from 'app/shared/model/my-post-response.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { MyPostResponseService } from './my-post-response.service';
import { MyPostResponseDeleteDialogComponent } from './my-post-response-delete-dialog.component';

@Component({
  selector: 'jhi-my-post-response',
  templateUrl: './my-post-response.component.html',
})
export class MyPostResponseComponent implements OnInit, OnDestroy {
  myPostResponses: IMyPostResponse[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected myPostResponseService: MyPostResponseService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.myPostResponses = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.myPostResponseService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IMyPostResponse[]>) => this.paginateMyPostResponses(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.myPostResponses = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMyPostResponses();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMyPostResponse): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMyPostResponses(): void {
    this.eventSubscriber = this.eventManager.subscribe('myPostResponseListModification', () => this.reset());
  }

  delete(myPostResponse: IMyPostResponse): void {
    const modalRef = this.modalService.open(MyPostResponseDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.myPostResponse = myPostResponse;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateMyPostResponses(data: IMyPostResponse[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.myPostResponses.push(data[i]);
      }
    }
  }
}

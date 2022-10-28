import styled from "styled-components";

import "App.css";

const Main = styled.div`
  box-sizing: border-box;
  display: grid;
  width: 1000px;
  grid-template-columns: repeat(6, 1fr);
  font-size: 20px;
  font-family: SCDream5;

  border-top: 1px solid black;
  border-left: 1px solid black;

  margin: 20px auto;
`;

const Name = styled.div`
  box-sizing: border-box;
  display: flex;
  align-items: center;
  justify-content: center;

  grid-row: 1 / 4;
  border-bottom: 1px solid black;
  border-right: 1px solid black;
`;

const MainBookTitle = styled(Name)`
  grid-row: 1 / 3;
`;
const SubBookTitle = styled.div`
  box-sizing: border-box;
  display: flex;
  align-items: center;
  justify-content: center;
  border-bottom: 1px solid black;
  border-right: 1px solid black;
`;

const Seanson = styled(SubBookTitle)``;
const MainBook = styled(SubBookTitle)`
  padding: 10px 0;
`;
const SubBook = styled(SubBookTitle)`
  padding: 10px 0;
`;
const BookImg = styled.img`
  width: 140px;
  height: 180px;
`;

const GrammarBookGuide = () => {
  return (
    <Main>
      <Name>
        Grammar
        <br />
        Level : A2
      </Name>
      <MainBookTitle>주교재</MainBookTitle>
      <Seanson>1분기</Seanson>
      <Seanson>2분기</Seanson>
      <Seanson>3분기</Seanson>
      <Seanson>4분기</Seanson>
      <MainBook>
        <BookImg src="https://image.aladin.co.kr/product/4908/94/cover500/110748053d_1.jpg"></BookImg>
      </MainBook>
      <MainBook>
        <BookImg src="https://image.aladin.co.kr/product/17176/42/cover500/3125354048_2.jpg"></BookImg>
      </MainBook>
      <MainBook>
        <BookImg src="http://image.kyobobook.co.kr/images/book/large/303/l9781107539303.jpg"></BookImg>
      </MainBook>
      <MainBook>
        <BookImg src="https://m.media-amazon.com/images/I/51bfeNEc4zL._SX382_BO1,204,203,200_.jpg"></BookImg>
      </MainBook>
      <SubBookTitle>부교재</SubBookTitle>
      <SubBook>
        <BookImg src="https://www.ebricks.co.kr/book/1124/BRICKS_ELTgrammar%20S_WB_%EC%82%AC%EC%9D%B4%ED%8A%B8%EC%9A%A9%20%EB%B3%B5%EC%82%AC.jpg"></BookImg>
      </SubBook>
      <SubBook>
        <BookImg src="https://www.ebricks.co.kr/book/1125/BRICKS_ELTgrammar%20S_WB_%EC%82%AC%EC%9D%B4%ED%8A%B8%EC%9A%A92%20%EB%B3%B5%EC%82%AC.jpg"></BookImg>
      </SubBook>
      <SubBook>
        <BookImg src="https://www.ebricks.co.kr/book/1126/BRICKS_ELTgrammar%20S_WB_%EC%82%AC%EC%9D%B4%ED%8A%B8%EC%9A%A93%20%EB%B3%B5%EC%82%AC.jpg"></BookImg>
      </SubBook>
      <SubBook></SubBook>
    </Main>
  );
};

export default GrammarBookGuide;

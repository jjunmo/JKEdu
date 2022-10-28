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
  position: relative;

  &:hover {
    > div {
      display: block;
    }
  }
`;
const SubBook = styled(SubBookTitle)`
  padding: 10px 0;
`;
const BookImg = styled.img`
  width: 140px;
  height: 180px;
`;

const BookExplain = styled.div`
  display: none;
  width: 400px;
  padding: 15px;
  left: 5px;
  z-index: 1;

  position: absolute;
  background-color: beige;
  border-radius: 15px;
`;

const SpeakingBookGuide = () => {
  return (
    <Main>
      <Name>
        Speaking
        <br />
        Level : B1
      </Name>
      <MainBookTitle>주교재</MainBookTitle>
      <Seanson>1분기</Seanson>
      <Seanson>2분기</Seanson>
      <Seanson>3분기</Seanson>
      <Seanson>4분기</Seanson>
      <MainBook>
        <a
          href="http://www.alist.co.kr/books_image/03SJ0001SB.jpg"
          target="_blank"
          rel="noopener noreferrer"
        >
          <BookImg src="http://www.alist.co.kr/books_image/03SJ0001SB.jpg"></BookImg>
        </a>
        <BookExplain>
          Bricks Reading은 Student Book + Workbook + E-Book 으로 이루어져있으며,
          학습대상은 G1 ~ G3, CEFR 레벨은 A1이 주로 학습하기에 적합합니다.
        </BookExplain>
      </MainBook>
      <MainBook>
        <BookImg src="http://www.alist.co.kr/books_image/03SJ0002WB.jpg"></BookImg>
        <BookExplain>
          Bricks Reading은 Student Book + Workbook + E-Book 으로 이루어져있으며,
          학습대상은 G1 ~ G3, CEFR 레벨은 A1이 주로 학습하기에 적합합니다.
        </BookExplain>
      </MainBook>
      <MainBook>
        <BookImg src="http://www.alist.co.kr/books_image/03SJ0003WB.jpg"></BookImg>
        <BookExplain>
          Bricks Reading은 Student Book + Workbook + E-Book 으로 이루어져있으며,
          학습대상은 G1 ~ G3, CEFR 레벨은 A1이 주로 학습하기에 적합합니다.
        </BookExplain>
      </MainBook>
      <MainBook>
        <BookImg src="https://www.alist.co.kr/books_image/04SCUE01SB.jpg"></BookImg>
        <BookExplain>
          Bricks Reading은 Student Book + Workbook + E-Book 으로 이루어져있으며,
          학습대상은 G1 ~ G3, CEFR 레벨은 A1이 주로 학습하기에 적합합니다.
        </BookExplain>
      </MainBook>
      <SubBookTitle>부교재</SubBookTitle>
      <SubBook>
        <BookImg src="https://www.ebricks.co.kr/book/847/PV_SS1.png"></BookImg>
      </SubBook>
      <SubBook>
        <BookImg src="https://www.ebricks.co.kr/book/848/PV_SS2.png"></BookImg>
      </SubBook>
      <SubBook>
        <BookImg src="https://www.ebricks.co.kr/book/849/PV_SS3.png"></BookImg>
      </SubBook>
      <SubBook></SubBook>
    </Main>
  );
};

export default SpeakingBookGuide;
